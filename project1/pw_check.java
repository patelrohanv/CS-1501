/**
 * @author Rohan
 * CS 1501
 * Fall 2016
 */
import java.util.*;
import java.io.*;
import java.lang.NullPointerException;
import java.lang.Double;

@SuppressWarnings("unchecked")
public class pw_check {
	public static char[] alphabet = "abcdefghijklmnopqrstuvwxyz1234567890!@$^_*".toCharArray();
	public static String al = "bcdefghjklmnopqrstuvwxyz23567890!@$^_*";
	public static Pass_ST all_times = new Pass_ST();
	public static DLB dlb;
	public static void main(String[] args){
		dlb = new DLB();
		if(args[0].equals("-find")){
			System.out.println("Generating passwords");
			enumerate_passwords();
			System.out.println("Finished Generating passwords");
		}
		else if (args[0].equals("-check")){
			check_password();
		}
		else{
			System.out.println("please enter valid command");
		}
	}
	/**
	 * Generates passwords
	 */
	public static void enumerate_passwords(){
		if(!load_dlb("dictionary.txt")){
			System.out.println("please add \"dictionary.txt\" to current directory");
		}
		try{
			PrintWriter good = new PrintWriter("all_passwords.txt");
			
	        for (Character a : alphabet) {
	        	double startTime = System.nanoTime();
	            String s1 = "" + a;
	            if(dlb.search(s1)){
	            	continue;
	            }
	            for (Character b : alphabet) {
	                String s2 = "" + a + b;
		            if(dlb.search(s2)){
		            	continue;
		            }
	                for (Character c : alphabet) {
	                    String s3 = "" + a + b + c;
	                    if(!valid_part(s3)){
	                    	continue;
	                    }
	    	            if(dlb.search(s3)){
	    	            	continue;
	    	            }
	                    for (Character d : alphabet) {
	                        String s4 = "" + a + b + c + d;
	                        if(!valid_part(s4)){
	                        	continue;
	                        }
	        	            if(dlb.search(s3)){
	        	            	continue;
	        	            }
	        	            if(dlb.search(""+b+c)){
	        	            	continue;
	        	            }
	        	            if(dlb.search(""+b+c+d)){
	        	            	continue;
	        	            }
	                        for (Character e : alphabet){
	                            String s = "" + a + b + c + d + e;
	            	            if(dlb.search(s)){
	            	            	continue;
	            	            }
		        	            if(dlb.search(""+b+c+d+e)){
		        	            	continue;
		        	            }
		        	            if(dlb.search(""+c+d+e)){
		        	            	continue;
		        	            }
		        	            if(dlb.search(""+d+e)){
		        	            	continue;
		        	            }
	                            if(valid(s)){
	                            	double endtime = System.nanoTime() - startTime;
	                            	endtime = endtime/1000000;
	                            	good.println(s + "," + endtime);
	                            	startTime = System.nanoTime();
	                            }
	                        }
	                    }
	                }
	            }
	        }
	        good.close();
		}
		catch(FileNotFoundException e){
			
		}
	}
	/**
	 * 
	 * @param s string of size 5 to check if valid password
	 * @return
	 */
	public static boolean valid(String s){
		if(s.contains("a") || s.contains("4") || s.contains("i") || s.contains("1")){ 
			return false;
		}
		int num_letters = 0;
		int num_num = 0;
		int num_char = 0;
		char[] a = s.toCharArray();
		for(int i = 0; i < a.length; i++){
			
			switch(a[i]){
				case 'b': num_letters++;
					break;
				case 'c': num_letters++;
					break;
				case 'd': num_letters++;
					break;
				case 'e': num_letters++;
					break;
				case 'f': num_letters++;
					break;
				case 'g': num_letters++;
					break;
				case 'h': num_letters++;
					break;
				case 'j': num_letters++;
					break;
				case 'k': num_letters++;
					break;
				case 'l': num_letters++;
					break;
				case 'm': num_letters++;
					break;
				case 'n': num_letters++;
					break;
				case 'o': num_letters++;
					break;
				case 'p': num_letters++;
					break;
				case 'q': num_letters++;
					break;
				case 'r': num_letters++;
					break;
				case 's': num_letters++;
					break;
				case 't': num_letters++;
					break;
				case 'u': num_letters++;
					break;
				case 'v': num_letters++;
					break;
				case 'w': num_letters++;
					break;
				case 'x': num_letters++;
					break;
				case 'y': num_letters++;
					break;
				case 'z': num_letters++;
					break;
				case '2': num_num++;
					break;
				case '3': num_num++;
					break;
				case '5': num_num++;
					break;
				case '6': num_num++;
					break;
				case '7': num_num++;
					break;
				case '8': num_num++;
					break;
				case '9': num_num++;
					break;
				case '0': num_num++;
					break;
				case '!': num_char++;
					break;
				case '@': num_char++;
					break;
				case '$': num_char++;
					break;
				case '^': num_char++;
					break;
				case '_': num_char++;
					break;
				case '*': num_char++;
					break;
			}
			//early pruning
			if(num_letters > 3){
				return false;
			}
			if(num_num > 2){
				return false;
			}
			if(num_char > 2){
				return false;
			}
			
		}
		if(num_letters < 1 || num_num < 1 || num_char < 1){
			return false;
		}
		return true;
	}
	/**
	 * 
	 * @param s string less than 5 long to check if valid password
	 * @return
	 */
	public static boolean valid_part(String s){
		if(s.contains("a") || s.contains("4") || s.contains("i") || s.contains("1")){ 
			return false;
		}
		int num_letters = 0;
		int num_num = 0;
		int num_char = 0;
		char[] a = s.toCharArray();
		for(int i = 0; i < a.length; i++){
			
			switch(a[i]){
				case 'b': num_letters++;
					break;
				case 'c': num_letters++;
					break;
				case 'd': num_letters++;
					break;
				case 'e': num_letters++;
					break;
				case 'f': num_letters++;
					break;
				case 'g': num_letters++;
					break;
				case 'h': num_letters++;
					break;
				case 'j': num_letters++;
					break;
				case 'k': num_letters++;
					break;
				case 'l': num_letters++;
					break;
				case 'm': num_letters++;
					break;
				case 'n': num_letters++;
					break;
				case 'o': num_letters++;
					break;
				case 'p': num_letters++;
					break;
				case 'q': num_letters++;
					break;
				case 'r': num_letters++;
					break;
				case 's': num_letters++;
					break;
				case 't': num_letters++;
					break;
				case 'u': num_letters++;
					break;
				case 'v': num_letters++;
					break;
				case 'w': num_letters++;
					break;
				case 'x': num_letters++;
					break;
				case 'y': num_letters++;
					break;
				case 'z': num_letters++;
					break;
				case '2': num_num++;
					break;
				case '3': num_num++;
					break;
				case '5': num_num++;
					break;
				case '6': num_num++;
					break;
				case '7': num_num++;
					break;
				case '8': num_num++;
					break;
				case '9': num_num++;
					break;
				case '0': num_num++;
					break;
				case '!': num_char++;
					break;
				case '@': num_char++;
					break;
				case '$': num_char++;
					break;
				case '^': num_char++;
					break;
				case '_': num_char++;
					break;
				case '*': num_char++;
					break;
			}
			//early pruning
			if(num_letters > 3){
				return false;
			}
			if(num_num > 2){
				return false;
			}
			if(num_char > 2){
				return false;
			}
		}
		return true;
	}
	/**
	 * checking passwords
	 */
	public static void check_password(){
		if(!load_dlb("all_passwords.txt")){
			System.out.println("please add \"all_passwords.txt\" to current directory or run \"java pw_check -find\"");
		}
		Scanner k = new Scanner(System.in);
		String c = "";
		String check;
		do{
			System.out.println("Select an option: \n\t1. Enter \"check\" to check a password\n\t2. Enter \"quit\" to Quit");
			c = k.next().toLowerCase();
			switch(c){
				case "check":
					System.out.println("Please enter the password you want to check");
					check = k.next();
					if(dlb.search(check)){
						System.out.println("Good password");
						System.out.println("Time to crack: " + all_times.get(check) + "ms");
					}
					else{
						System.out.println("bad password");
						System.out.println("Getting potential other passwords: ");
						reccommend(check);
					}
					break;
				case "quit":
					break;
				default:
					System.out.println("Please enter valid option");
				}
		}while(!c.equals("quit"));
		k.close();
	}
	public static boolean load_dlb(String s){
		System.out.println("Please wait, loading DLB and/or Symbol Table");
		try{
			Scanner inFile = new Scanner(new File(s));
            while(inFile.hasNextLine()){
                //add each line into dlb
            	if(s.equals("all_passwords.txt")){
            		String a = inFile.nextLine();
            		//System.out.println(a);
            		String sub = a.substring(0, 5);
            		String time = a.substring(6);
            		//System.out.println(sub);
            		//System.out.println(time);
            		double t = Double.parseDouble(time);
            		dlb.add(sub);
            		all_times.add(sub, t);
            	}
            	else{
            		//* You only need to keep in mind the number/symbol letter replacements listed above when looking for dictionary words:  
            		//"7" for "t", "4" for "a", "0" for "o", "3" for "e", "1" for "i", "1" for "l", or "$" for "s". 
            		//You do not need to consider any other substitutions
            		String dict = (inFile.nextLine()).toLowerCase();
            		//dlb.add((inFile.nextLine()).toLowerCase());
					dlb.add(dict);
					String sub;
					if(dict.contains("t")){
						sub = dict.replace('t', '7');
						dlb.add(sub);
					}
					if(dict.contains("a")){
						sub = dict.replace('a', '4');
						dlb.add(sub);
					}
					if(dict.contains("o")){
						sub = dict.replace('o', '0');
						dlb.add(sub);
					}
					if(dict.contains("e")){
						sub = dict.replace('e', '3');
						dlb.add(sub);
					}
					if(dict.contains("i")){
						sub = dict.replace('i', '1');
						dlb.add(sub);
					}
					if(dict.contains("l")){
						sub = dict.replace('l', '1');
						dlb.add(sub);
					}
					if(dict.contains("s")){
						sub = dict.replace('s', '$');
						dlb.add(sub);
					}
            	}
            }
            inFile.close();
            System.out.println("loaded DLB and/or Symbol Table");
			return true;
		}
		catch(FileNotFoundException e){
			return false;
		}
		
	}
	/**
	 * 
	 * @param s string to build reccommendations off
	 */
	public static void reccommend(String s){
			boolean f = false;
			int i = 0;
			//first 4 letters
			if(valid_part(s.substring(0, 4))){
				System.out.println("Could not find long enough prefix to generate reccommendations");
				return;
				//s = s.substring(0, 4);
				//i = 4;
			}
			//first 3 letters
			else if(valid_part(s.substring(0, 3))){
				System.out.println("Could not find long enough prefix to generate reccommendations");
				return;
//				s = s.substring(0, 3);
//				i = 3;
			}
			//first 2 letters
			else if (!contains_a4i1(s.substring(0, 2))){
				System.out.println("Could not find long enough prefix to generate reccommendations");
				return;
				//s = s.substring(0, 2);
				//i = 2;
			}
			//first letter
			else if (s.charAt(0) != 'a' && s.charAt(0) != '4' && s.charAt(0) != 'i' && s.charAt(0) != '1'){
				System.out.println("Could not find long enough prefix to generate reccommendations");
				return;
				//s = s.substring(0, 1);
				//i = 1;
			}
			//no letters
			else{
				System.out.println("Could not find long enough prefix to generate reccommendations");
				return;
				//s = "";
			}
			//int original_i = i;
//			while(!f){
//				i = original_i;
//				for(; i < 5; i++){
//					int r = (int)(Math.random() * 38 + 1);
//					switch(r){
//					case 1:
//						s += "*";
//						break;
//					case 2:
//						s += "b";
//						break;
//					case 3:
//						s += "c";
//						break;
//					case 4:
//						s += "d";
//						break;
//					case 5:
//						s += "e";
//						break;
//					case 6:
//						s += "f";
//						break;
//					case 7:
//						s += "g";
//						break;
//					case 8:
//						s += "h";
//						break;
//					case 9:
//						s += "_";
//						break;
//					case 10:
//						s += "j";
//						break;
//					case 11:
//						s += "k";
//						break;
//					case 12:
//						s += "l";
//						break;
//					case 13:
//						s += "m";
//						break;
//					case 14:
//						s += "n";
//						break;
//					case 15:
//						s += "o";
//						break;
//					case 16:
//						s += "p";
//						break;
//					case 17:
//						s += "q";
//						break;
//					case 18:
//						s += "r";
//						break;
//					case 19:
//						s += "s";
//						break;
//					case 20:
//						s += "t";
//						break;
//					case 21:
//						s += "u";
//						break;
//					case 22:
//						s += "v";
//						break;
//					case 23:
//						s += "w";
//						break;
//					case 24:
//						s += "x";
//						break;
//					case 25:
//						s += "y";
//						break;
//					case 26:
//						s += "z";
//						break;
//					case 27:
//						s += "^";
//						break;
//					case 28:
//						s += "2";
//						break;
//					case 29:
//						s += "3";
//						break;
//					case 30:
//						s += "$";
//						break;
//					case 31:
//						s += "5";
//						break;
//					case 32:
//						s += "6";
//						break;
//					case 33:
//						s += "7";
//						break;
//					case 34:
//						s += "8";
//						break;
//					case 35:
//						s += "9";
//						break;
//					case 36:
//						s += "0";
//						break;
//					//specifically "!", "@", "$", "^", "_", or "*")
//					case 37:
//						s += "!";
//						break;
//					case 38:
//						s += "@";
//						break;
//
//				}
//				if(valid(s)){
//					System.out.println( s + " " + all_times.get(s) + "ms");
//					f = true;
//				}
//			}
//		}
//		for(int j = 0; j < 9; j++){
//			String sub_s = s.substring(0, 4);
//			boolean f2 = false;
//			while(!f2){
//				int r = (int)(Math.random() * 38 + 1);
//				switch(r){
//				case 1:
//					sub_s += "*";
//					break;
//				case 2:
//					sub_s += "b";
//					break;
//				case 3:
//					sub_s += "c";
//					break;
//				case 4:
//					sub_s += "d";
//					break;
//				case 5:
//					sub_s += "e";
//					break;
//				case 6:
//					sub_s += "f";
//					break;
//				case 7:
//					sub_s += "g";
//					break;
//				case 8:
//					sub_s += "h";
//					break;
//				case 9:
//					sub_s += "_";
//					break;
//				case 10:
//					sub_s += "j";
//					break;
//				case 11:
//					sub_s += "k";
//					break;
//				case 12:
//					sub_s += "l";
//					break;
//				case 13:
//					sub_s += "m";
//					break;
//				case 14:
//					sub_s += "n";
//					break;
//				case 15:
//					sub_s += "o";
//					break;
//				case 16:
//					sub_s += "p";
//					break;
//				case 17:
//					sub_s += "q";
//					break;
//				case 18:
//					sub_s += "r";
//					break;
//				case 19:
//					sub_s += "s";
//					break;
//				case 20:
//					sub_s += "t";
//					break;
//				case 21:
//					sub_s += "u";
//					break;
//				case 22:
//					sub_s += "v";
//					break;
//				case 23:
//					sub_s += "w";
//					break;
//				case 24:
//					sub_s += "x";
//					break;
//				case 25:
//					sub_s += "y";
//					break;
//				case 26:
//					sub_s += "z";
//					break;
//				case 27:
//					sub_s += "^";
//					break;
//				case 28:
//					sub_s += "2";
//					break;
//				case 29:
//					sub_s += "3";
//					break;
//				case 30:
//					sub_s += "$";
//					break;
//				case 31:
//					sub_s += "5";
//					break;
//				case 32:
//					sub_s += "6";
//					break;
//				case 33:
//					sub_s += "7";
//					break;
//				case 34:
//					sub_s += "8";
//					break;
//				case 35:
//					sub_s += "9";
//					break;
//				case 36:
//					sub_s += "0";
//					break;
//				//specifically "!", "@", "$", "^", "_", or "*")
//				case 37:
//					sub_s += "!";
//					break;
//				case 38:
//					sub_s += "@";
//					break;
//
//			}
//			if(valid(sub_s)){
//				System.out.println( sub_s + " " + all_times.get(sub_s) + "ms");
//				f = true;
//			}
//			}
//		}
	}
	/**
	 * 
	 * @param s string to check if it has a4i1
	 * @return
	 */
	public static boolean contains_a4i1 (String s){
		if(s.contains("a") || s.contains("4") || s.contains("i") || s.contains("1")){ 
			return false;
		}
		else{
			return true;
		}
	}
}
