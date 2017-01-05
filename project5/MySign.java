/*
Write a second program named MySign to sign files and verify signatures.  T
his program should take in two command line arguments,
a flag to specify whether it should be signing or verifying ("s" or "v"),
and the file that should be signed or verified.
    1.  If called to sign (e.g., "java MySign s myfile.txt") your program should:
         1.  Generate a SHA-256 hash of the contents of the provided file (e.g., "myfile.txt")
         1.  "decrypt" this hash value using the private key stored in privkey.rsa (i.e.,
         raise it to the D<sup>th</sup> power mod N)
              *  Your program should exit and display an error
              if privkey.rsa is not found in the current directory
         1.  Write out a signed version of the file (e.g., "myfile.txt.signed") that contains:
              *  The contents of the original file
              *  The "decrypted" hash of the original file
    1.  If called to verify (e.g., "java MySign v myfile.txt.signed") your program should:
         1.  Read the contents of the original file
         1.  Generate a SHA-256 hash of the contents of the orignal file
         1.  Read the "decrypted" hash of the original file
         1.  "encrypt" this value with the contents of pubkey.rsa (i.e.,
         raise it to the E<sup>th</sup> power mod N)
              *  Your program should exit and display an error
              if pubkey.rsa is not found in the current directory
         1.  Compare these two hash values
         (the on newly generated and the one that you just "encrypted")
         and print out to the console whether or not the signature is valid (i.e.,
         whether or not the values are the same).
*/
import java.util.Scanner;
import java.util.Arrays;
import java.io.*;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.math.BigInteger;
import java.security.MessageDigest;

public class MySign{
     public static void main(String[] args){
          BigInteger E = BigInteger.ZERO;
          BigInteger N = BigInteger.ZERO;
          BigInteger D = BigInteger.ZERO;
          BigInteger sign = BigInteger.ZERO;
          //public key read E N
          //private key read D N
          try{
               if(args[0].equals("s")){ // "decrypt" using privkey
                    FileInputStream priv = new FileInputStream(new File("privkey.rsa"));
                    ObjectInputStream privkey = new ObjectInputStream(priv);
                    D = (BigInteger) privkey.readObject();
                    N = (BigInteger) privkey.readObject();
                    //get D<sup>th</sup> power mod N
                    //m = c^d(mod n)
                    // read in the file to hash
     			Path path = Paths.get(args[1]);
     			byte[] data = Files.readAllBytes(path);

     			// create class instance to create SHA-256 hash
     			MessageDigest md = MessageDigest.getInstance("SHA-256");

     			// process the file
     			md.update(data);
     			// generate a has of the file
     			byte[] digest = md.digest();

     			// note that conversion to biginteger will remove any leading 0s in the bytes of the array!
                    BigInteger M = new BigInteger(digest);
                    sign = new BigInteger(digest);
                    sign = sign.modPow(D,N);

                    String signedFile = args[1] + ".signed";
                    FileOutputStream signed = new FileOutputStream(new File(signedFile));
                    ObjectOutputStream signedObject = new ObjectOutputStream(signed);
                    signedObject.writeObject(M);
                    signedObject.writeObject(sign);
                    signedObject.close();
                    signed.close();
                    privkey.close();
                    priv.close();

                    // System.out.println("N: " + N);
                    // System.out.println("Original: " + M);
                    // System.out.println("Sign: " + sign);

               }
               else if(args[0].equals("v")){ //"encrypt" with public key
                    FileInputStream pub = new FileInputStream(new File("pubkey.rsa"));
                    ObjectInputStream pubkey = new ObjectInputStream(pub);
                    E = (BigInteger) pubkey.readObject();
                    N = (BigInteger) pubkey.readObject();
                    //get E<sup>th</sup> power mod N
                    //c = m^e(mod n)
                    FileInputStream signedFile = new FileInputStream(new File(args[1]));
                    ObjectInputStream signedFileObject = new ObjectInputStream(signedFile);
                    BigInteger M = (BigInteger) signedFileObject.readObject();
                    sign = (BigInteger) signedFileObject.readObject();
                    BigInteger verify = M.modPow(E,N);
                    if(verify.equals(sign)){
                         System.out.println("Valid");
                    }
                    else{
                         System.out.println("Not Valid");
                    }
                    signedFileObject.close();
                    signedFile.close();
                    pubkey.close();
                    pub.close();

                    // System.out.println("N: " + N);
                    // System.out.println("Original: " + M);
                    // System.out.println("Sign: " + verify);
               }
               else{
                    System.out.println("Invalid command line args");
                    System.exit(0);
               }
          }
          catch(Exception e) {
               System.out.println("File not found");
			System.out.println(e.toString());
		}
     }
}
