/*
.  Write a program named MyKeyGen to generate a new keypair
	1.  To generate a keypair,
     you will need to follow these steps as described in lecture:
		1.  Pick P and Q to be random primes of an appropriate size
          to generate a 1024 bit key
		1.  Generate N as P * Q
		1.  Generate PHI(N) as (P-1) * (Q-1)
		1.  Pick E such that 1 < E < PHI(N) and GCD(E, PHI(N))=1
          (E must not divide PHI(N) evenly)
		1.  Pick D such that D = E<sup>-1</sup> mod PHI(N)
	1.  After generating E, D, and N,
     save E and N to pubkey.rsa and D and N to privkey.rsa
*/
import java.math.BigInteger;
import java.util.Random;
import java.io.*;
public class MyKeyGen{
     public static void main(String[] args){
          BigInteger P = BigInteger.ZERO;
          BigInteger Q = BigInteger.ZERO;
          BigInteger N = BigInteger.ZERO;
          BigInteger P_minus_1 = BigInteger.ZERO;
          BigInteger Q_minus_1 = BigInteger.ZERO;
          BigInteger PHI_N = BigInteger.ZERO;
          BigInteger E = BigInteger.ZERO;
          BigInteger D = BigInteger.ZERO;
          boolean flag;
          boolean allVarsSet = false;
          Random r = new Random();

          flag = false;
          while(!flag){
               P = BigInteger.probablePrime(512, r);
               if(!P.isProbablePrime(0)){
                    P = P.nextProbablePrime();
               }
               Q = BigInteger.probablePrime(512, r);
               if(!Q.isProbablePrime(0)){
                    Q = Q.nextProbablePrime();
               }
               N = P.multiply(Q);
               if(N.bitLength() != 1024){
                    continue;
               }
               flag = true;
          }
          // System.out.println("P: " + P);
          // System.out.println(P.bitLength());
          // System.out.println("Q: " + Q);
          // System.out.println(Q.bitLength());
          // System.out.println("N: " + N);
          // System.out.println(N.bitLength());

          P_minus_1 = P.subtract(BigInteger.ONE);
          Q_minus_1 = Q.subtract(BigInteger.ONE);
          PHI_N = P_minus_1.multiply(Q_minus_1);

          // System.out.println("P-1: " + P_minus_1);
          // System.out.println(P_minus_1.bitLength());
          // System.out.println("Q-1: " + Q_minus_1);
          // System.out.println(Q_minus_1.bitLength());
          // System.out.println("PHI(N): " + PHI_N);
          // System.out.println(PHI_N.bitLength());

          flag = false;
          while(!flag){
               E = new BigInteger(1024, r);
               //cannot be less than one
               if(E.compareTo(BigInteger.ONE) == -1){
                    continue;
               }
               //cannot be equal to one
               if(E.compareTo(BigInteger.ONE) == 0){
                    continue;
               }
               //cannot be greater than PHI(N)
               if(E.compareTo(PHI_N) == 1){
                    continue;
               }
               //cannot equal PHI(N)
               if(E.compareTo(PHI_N) == 0){
                    continue;
               }
               // GCD(E, PHI(N))=1
               BigInteger GCD = E.gcd(PHI_N);
               if(GCD.compareTo(BigInteger.ONE) !=0){
                    continue;
               }
               flag = true;
               D = E.modInverse(PHI_N);
               //all variables have now been set
               allVarsSet = true;
          }

          // System.out.println("E: " + E);
          // System.out.println(E.bitLength());
          // System.out.println("D: " + D);
          // System.out.println(D.bitLength());

          if(allVarsSet){
               try{
                    //encrypt (sign)
                    FileOutputStream pub = new FileOutputStream(new File("pubkey.rsa"));
                    ObjectOutputStream pubkey = new ObjectOutputStream(pub);
                    pubkey.writeObject(E);
                    pubkey.writeObject(N);
                    pubkey.close();
                    pub.close();
                    //decrypt (verify)
                    FileOutputStream priv = new FileOutputStream(new File("privkey.rsa"));
                    ObjectOutputStream privkey = new ObjectOutputStream(priv);
                    privkey.writeObject(D);
                    privkey.writeObject(N);
                    privkey.close();
                    priv.close();
               }
               catch (IOException e){
                    System.out.println(e);
               }
          }
     }
}
