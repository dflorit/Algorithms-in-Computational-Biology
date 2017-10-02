
public class Kmp {

	public static void main(String[] args) {
		//Input string
		String text = " bacbabababacacaababaca";
		String pattern = " ababaca";
		    	
		char[] textChar = text.toCharArray();
		char[] patternChar = pattern.toCharArray();
		
		kmp(patternChar, textChar);
		
	}
	
	private static int[] computePrefixFunction(char[] patternChar){
		int m = patternChar.length;
		int k = 0;
		int[] pi = new int[m];
		pi[1] = 0;
		
		for(int q = 2; q < m; q++){
			while(k >0 && patternChar[k+1] != patternChar[q]){
				k = pi[k];
			}
			if(patternChar[k+1] == patternChar[q]){
				k++;
			}
			pi[q] = k;
		}
		
		return pi;
	}
	
	private static void kmp(char[] patternChar, char[] textChar){
		int[] pi = computePrefixFunction(patternChar);
		int n = textChar.length;
		int m = patternChar.length;
		int q = 0;
		
		//printing pi, just to visualize it
		printArray(pi);
		
		for(int i = 1; i < n; i++){
			while(q>0 && patternChar[q+1] != textChar[i]){
				q = pi[q];
			}
			
			if(patternChar[q+1] == textChar[i]){
				q++;
			}
			if(q + 1 == m){
				System.out.println("Pattern occurs with shift " + Integer.toString(i-m+1));
				q = pi[q];
			}
		}
		
		
	}
	private static void printArray(int[] arr){
		String arrString = "";
		for(int i = 1; i < arr.length; i++){
			arrString += Integer.toString(arr[i]) + " ";
		}
		System.out.println(arrString);
	}

}
