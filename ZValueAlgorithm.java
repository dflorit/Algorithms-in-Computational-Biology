/* 
 * Question:
 * For each of the  prefixes of P, we want to know whether the prefix P[1..i] is a periodic string. That is, for each i, 
 * we want to know the largest k > 1 (if there exist one) such that P[1..i] can be written as alpha^k for some string alpha. 
 * Of course, we also want to know the period
 * Give an algorithm to determine this for all alpha prefixes in time linear in the length of P.
 * 
 * Input: a string P (which is denominated as text in the code)
 * Output: Print a matrix containing the text string, the z-values, the values of k, the period if it exists, and True/False 
 * referring to whether the prefix is a periodic string or not.
 * 
 * */

public class ZValueAlgorithm {

	public static void main(String args[]) {
    	//Input string
		String text = "aaacaaacaaacaaac";
    	//String text = "aabaabaabaac";
    	//String text = "aba";
    	
		//Convert input string text to array of characters
		char[] textChar = text.toCharArray();
		
		//Calculate Z values
        int[] zValues = calculateZValue(textChar);
        findRepeats(zValues, textChar);
    }
	
	private static int[] calculateZValue(char[] textChar) {
        int[] Z = new int[textChar.length];
        int left = 0;
        int right = 0;
        
        for(int k = 1; k < textChar.length; k++) {
            if(k > right) {
                left = right = k;
                while(right < textChar.length && textChar[right] == textChar[right - left]) {
                    right++;
                }
                Z[k] = right - left;
                right--;
            } else {
                int k1 = k - left;
                
                if(Z[k1] < right - k + 1) {
                    Z[k] = Z[k1];
                } else { 
                    left = k;
                    while(right < textChar.length && textChar[right] == textChar[right - left]) {
                        right++;
                    }
                    Z[k] = right - left;
                    right--;
                }
            }
            //System.out.println("Left: " + (left + 1) + " Right: " + (right +1)  + " Z[" + (k+1) + "] = "+ Z[k]);
        }
        return Z;
    }

    public static void findRepeats(int[] zValues, char[] text){
    	int n = zValues.length;
    	boolean[] repeats = new boolean[n];
    	int[] ks =  new int[n];
    	int[] periodSize = new int[n];
    	
    	repeats[0] = false;
    	int z = 0;
    	int div = 0;
    	int position = 0;
    	
    	for(int i = 1; i < zValues.length/2; i++){
    		z = zValues[i];
    		if(repeats[i] != true){
    			if(z == 0){
    				repeats[i] = false;
    			}
    			else if(z >= i){
        			div = Math.floorDiv(z, i);
        			for(int j = 1; j < (div +1); j++){
        				position = i*(j+1)-1;
        				repeats[position] = true;
        				ks[position] = j+1;
        				periodSize[position] = i;
        			}
        		}
    			else {
    				repeats[i] = false;
    			}
    		}
    	}
    	printMatrix(text, zValues, repeats, ks, periodSize);
    }
    	
    public static void printMatrix(char[] text, int[] zValues, boolean[] repeats, int[] ks, int[] periodSize){
    	int n = text.length;
    	
    	String[][] easyGrid = new String[6][n+1];
    	easyGrid[0][0] = "index: ";
    	easyGrid[1][0] = "text: ";
    	easyGrid[2][0] = "Zval: ";
    	easyGrid[3][0] = "T/F: ";
    	easyGrid[4][0] = "k: ";
    	easyGrid[5][0] = "Period: ";
    	
    	for(int i = 0; i < text.length; i++){
    		easyGrid[0][i+1] = Integer.toString(i);
    		easyGrid[1][i+1] = Character.toString(text[i]);
    		easyGrid[2][i+1] = Integer.toString(zValues[i]);
    		easyGrid[4][i+1] = Integer.toString(ks[i]);
    		
    		String s = "";
    		for(int j = 0; j < periodSize[i]; j++){
    			s += Character.toString(text[j]);
    		}
    		
    		easyGrid[5][i+1] = s;
    		
    		if(repeats[i] == true){
    			easyGrid[3][i+1] = "T";
    		}
    		else{
    			easyGrid[3][i+1] = "F";
    		}
    	}
    	
    	for(int o = 0; o < 6; o++){
    		for(int j = 0;j <  7 * n ;j++){ 
        		System.out.print("-");    
        	}
               System.out.println("-");
            for(int k = 1;k <= easyGrid[o].length; k++){
            	System.out.printf("|%5s ",easyGrid[o][k - 1]);
            }
            	System.out.println("|");           
    	}
    }
}
