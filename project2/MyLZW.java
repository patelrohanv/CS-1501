/*************************************************************************
 *  Rohan Patel
 *  Assignment 2
 *  CS 1501
 *  Fall 2016
 *
 *  Compilation:  javac MyLZW.java
 *  Execution:    java MyLZW - < input >    (compress)
 *  Execution:    java MyLZW - r < input >  (compress-reset)
 *  Execution:    java MyLZW - m < input >  (compress-monitor)
 *  Execution:    java MyLZW + < input >    (expand)
 *
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using MyLZW.
 *
 *************************************************************************/

public class MyLZW {
    private static final int R = 256;        // number of input chars
    private static int L = 512;       // number of codewords = 2^W
    private static int W = 9;         // codeword width, inital 9, max 16
    private static boolean monitor_mode = false;
    private static double new_ratio = 0;
    private static double old_ratio = 0; 
    private static int original_size = 0;
    private static int compressed = 0;
    private static final int max_L = 65536; //max number of codewords
    private static char compress_expand;
    private static char compression_mode;


    public static void compress() { 
        BinaryStdOut.write(compression_mode, 8); //saves compression mode

        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++){
            st.put("" + (char) i, i);
        }
        int code = R+1;  // R is codeword for EOF

        while (input.length() > 0) {
            L = (int)Math.pow(2,W);
            String s = st.longestPrefixOf(input);  // Find max prefix match s.

            original_size += s.length() * 8;
            compressed += W;
            old_ratio = original_size/compressed;

            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
            int t = s.length();
            if (t < input.length() && code < L){    // Add s to symbol table.
                st.put(input.substring(0, t + 1), code++);
            }

            if(W < 16  && L == code){ 
            //if W is less than max and code = the number of codewords
                W++; //increase W
                L = (int)Math.pow(2,W); //by extension, increase L
                st.put(input.substring(0, t + 1), code++);
            }

            if(compression_mode == 'r' && code == max_L){
                st = new TST<Integer>();
                for (int i = 0; i < R; i++){
                    st.put("" + (char) i, i);
                }
                code = R+1;  // R is codeword for EOF
                W = 9;
                L = (int)Math.pow(2,W);
            }
            else if (compression_mode == 'm' && code == max_L){
                if(!monitor_mode){
                    monitor_mode = true;
                    new_ratio = old_ratio;
                }

                if(new_ratio/old_ratio > 1.1){
                    st = new TST<Integer>();
                    for (int i = 0; i < R; i++){
                        st.put("" + (char) i, i);
                    }
                    code = R+1;  // R is codeword for EOF
                    W = 9;
                    L = (int)Math.pow(2,W);
                    old_ratio = 0;
                    new_ratio = 0;
                    monitor_mode = false;
                }
            }
            input = input.substring(t);            // Scan past s in input.
        }
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    } 


    public static void expand() {
        compression_mode = BinaryStdIn.readChar(8); //gets compression mode

        String[] st = new String[max_L];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++){
            st[i] = "" + (char) i;
        }
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        if (codeword == R){
            return;
        }     // expanded message is empty string
        String val = st[codeword];

        while (true) {
            BinaryStdOut.write(val);

            original_size += val.length() * 8;
            compressed += W;
            old_ratio = original_size/compressed;

            codeword = BinaryStdIn.readInt(W);
            if (codeword == R){
                break;
            }
            String s = st[codeword];
            if (i == codeword){
                s = val + val.charAt(0);   // special case hack
            }
            // if (i < L){
            //     st[i++] = val + s.charAt(0);
            // }
            if (i < L-1){
                st[i++] = val + s.charAt(0);
            }
            if (i == L-1 && W < 16){
                st[i++] = val + s.charAt(0);
                W++;
                L = (int)Math.pow(2, W);
            }

            val = s;

            //if (compression_mode == 'r' && i == max_L){
            if (compression_mode == 'r' && i == max_L -1){
                st = new String[max_L];
                for (i = 0; i < R; i++){
                    st[i] = "" + (char) i;
                }
                W = 9;
                L = (int)Math.pow(2, W);
            }
            //else if (compression_mode == 'm' && i == max_L){
            else if (compression_mode == 'm' && i == max_L -1){
                if(!monitor_mode){
                    monitor_mode = true;
                    new_ratio = old_ratio;
                }

                if(new_ratio/old_ratio > 1.1){
                    st = new String[max_L];
                    for (i = 0; i < R; i++){
                        st[i] = "" + (char) i;
                    }
                    W = 9;
                    L = (int)Math.pow(2,W);
                    old_ratio = 0;
                    new_ratio = 0;
                    monitor_mode = false;
                }
            }
        }
        BinaryStdOut.close();
    }



    public static void main(String[] args) {
        compress_expand = args[0].charAt(0);
        if (compress_expand == '-'){
            compression_mode = args[1].charAt(0);
            compress();
        }
        else if(compress_expand == '+'){
            expand();
        }
        else{
            throw new IllegalArgumentException("Illegal command line argument");
        }
    }

}
