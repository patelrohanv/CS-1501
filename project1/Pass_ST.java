/**
 * @author Rohan
 *CS 1501
 *Fall 2016
 */
import java.lang.ArrayIndexOutOfBoundsException;

public class Pass_ST {
	private double[] times;
	public Pass_ST(){
		int i = (int) (Math.pow(42, 0) + Math.pow(42, 1) + Math.pow(42, 2) + Math.pow(42, 3) +Math.pow(42, 4) + Math.pow(42, 5));
		times = new double[i];
	}
	/**
	 * 
	 * @param s string to search time for
	 * @return
	 */
	public double get(String s){
		try{
			int index = 0;
			for(int i = 0; i < s.length(); i++){
				index = hash_value(s.charAt(i)) + (int) Math.pow(42, i);
			}
			return times[index];
		}	 
		 catch(ArrayIndexOutOfBoundsException e){
			 System.out.println("Index error");
			 return -1.00;
		 }
	}
	/**
	 * 
	 * @param s string to add
	 * @param t time to add
	 */
	public void add(String s, double t){
		int index = 0;
		for(int i = 0; i < s.length(); i++){
			index = hash_value(s.charAt(i)) + (int) Math.pow(42, i);
		}
		times[index] = t;
	}
	/**
	 * 
	 * @param c hash value of char
	 * @return
	 */
	public int hash_value(char c){
		int ret = 0;
		switch(c){
		case 'a': return 1;
			
		case 'b': return 2;
			
		case 'c': return 3;
			
		case 'd': return 4;
			
		case 'e': return 5;
			
		case 'f': return 6;
			
		case 'g': return 7;
			
		case 'h': return 8 ;
		
		case 'i': return 9;
			
		case 'j': return 10;
			
		case 'k': return 11;
			
		case 'l': return 12;
			
		case 'm': return 13;
			
		case 'n': return 14;
			
		case 'o': return 15;
			
		case 'p': return 16;
			
		case 'q': return 17;
			
		case 'r': return 18;
			
		case 's': return 19;
			
		case 't': return 20;
			
		case 'u': return 21;
			
		case 'v': return 22;
			
		case 'w': return 23;
			
		case 'x': return 24;
			
		case 'y': return 25;
			
		case 'z': return 26;
		
		case '1': return 27;
			
		case '2': return 28;
			
		case '3': return 29;
			
		case '4': return 30;
		
		case '5': return 31;
			
		case '6': return 32;
			
		case '7': return 33;
			
		case '8': return 34;
			
		case '9': return 35;
			
		case '0': return 36;
			
		case '!': return 37;
			
		case '@': return 38;
			
		case '$': return 39;
			
		case '^': return 40;
			
		case '_': return 41;
			
		case '*': return 42;
			
		}
		if (ret > 0){
			return ret;
		}
		else{
			return 0;
		}
	}
	public static void main(String[] args){
		Pass_ST test = new Pass_ST();
		System.out.println("did not allocate too much space");
	}
}
