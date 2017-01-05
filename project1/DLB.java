/**
 * 
 * @author Rohan
 * CS 1501
 * Fall 2016
 */
public class DLB {
	DLB_Node root;
	public DLB(){
		root = new DLB_Node();
	}
/**
 * 
 * @param s string to add to dlb
 * @return
 */
	public boolean add(String s){
		DLB_Node n = root;
		s += "#";
		char[] arr = s.toCharArray();
		for(int i = 0; i <  s.length(); i++){
			if(n.getDown() == null){
				n.setDown(new DLB_Node(arr[i]));
				n = n.getDown();
				continue;
			}
			if(n.getDown() != null && n.getDown().getLetter() == arr[i]){
				n = n.getDown();
				continue;
			}
			else if(n.getDown() != null && n.getDown().getLetter() != arr[i]){
				if(n.getDown().getRight() == null){
					n.getDown().setRight(new DLB_Node(arr[i]));
					n = n.getDown().getRight();
					continue;
				}
				else if(n.getDown().getRight() != null){
					//System.out.println("here");
					n = n.getDown();
					boolean f = false;
					while(n.getRight() != null){
						if(n.getRight().getLetter() == arr[i] && !f){
							//System.out.println("here2");
							n = n.getRight();
							f = true;
						}
						else{
							//System.out.println("here3");
							n = n.getRight();
						}
					}
					if(!f){
						//System.out.println("here4");
						n.setRight(new DLB_Node(arr[i]));
						n = n.getRight();
						continue;
					}
					
				}
			}
		}
		if(n.getLetter() == '#'){
			return true;
		}
		else{
			return false;
		}
		
	}
	/**
	 * 
	 * @param s string to search for
	 * @return
	 */
	public boolean search(String s){
		s += "#";
		DLB_Node n = root.getDown();
		char[] arr = s.toCharArray();
		boolean f = false;
		for(int i = 0; i < arr.length; i++){
			boolean brk = false;
			if(n.getLetter() == '#' && arr[i] == '#'){
				return true;
			}
			else if(n.getLetter() == arr[i]){
				if(n.getDown() == null){
					return false;
				}
				else{
					n = n.getDown();
				}
			}
			else if(n.getLetter() != arr[i]){
				while(n.getRight() != null && !f){
					n = n.getRight();
					if(n.getLetter() == arr[i]){
						if(n.getDown() != null){
							n = n.getDown(); //could potentially break;
							brk = true;
							//f = true;
							break;
						}
					}
					//f = true;
				}
				if(brk){
					continue;
				}
				return false;
			}
		}
		
		return false;
	}
	/**
	 * Inner node class for dlb 
	 * 
	 */
	class DLB_Node{
		public char getLetter() {
			return letter;
		}
		public void setLetter(char letter) {
			this.letter = letter;
		}
		public DLB_Node getRight() {
			return right;
		}
		public void setRight(DLB_Node right) {
			this.right = right;
		}
		public DLB_Node getDown() {
			return down;
		}
		public void setDown(DLB_Node down) {
			this.down = down;
		}
		public boolean hasRight(){
			if(right != null){
				return true;
			}
			else{
				return false;
			}
		}
		public boolean hasDown(){
			if(down != null){
				return true;
			}
			else{
				return false;
			}
		}
		public DLB_Node(char letter) {
			this.letter = letter;
			right = null;
			down = null;
		}
		public DLB_Node(){
			right = null;
			down = null;
		}
		char letter;
		DLB_Node right;
		DLB_Node down;
		
	}
	
	/**
	 * Test driver for class
	 * @param args no args
	 */
	public static void main (String[] args){
		DLB test = new DLB();
		/****************TESTING ADD****************/
		System.out.println("BEGIN TEST ADD");
		System.out.println("Sally: " + test.add("sally"));
		System.out.println("sells: " + test.add("sells"));
		System.out.println("a: " + test.add("a"));
		System.out.println("sea: " + test.add("sea"));
		System.out.println("shell: " + test.add("shell"));
		System.out.println("by: " + test.add("by"));
		System.out.println("the: " + test.add("the"));
		System.out.println("blue: " + test.add("blue"));
		System.out.println("and: " + test.add("and"));
		System.out.println("shore: " + test.add("shore"));
		/****************TESTING SEARCH****************/
		System.out.println("_________________________________");
		System.out.println("BEGIN TEST SEARCH");
		System.out.println("Sally: " + test.search("sally"));
		System.out.println("sells: " + test.search("sells"));
		System.out.println("a: " + test.search("a"));
		System.out.println("sea: " + test.search("sea"));
		System.out.println("shell: " + test.search("shell"));
		System.out.println("by: " + test.search("by"));
		System.out.println("the: " + test.search("the"));
		System.out.println("blue: " + test.search("blue"));
		System.out.println("and: " + test.search("and"));
		System.out.println("shore: " + test.search("shore"));

	}
}
