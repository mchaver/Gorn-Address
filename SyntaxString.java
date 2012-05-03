
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SyntaxString {
	private char left = '(';
	private char right = ')';
	public String syntaxTree = "";
	public String gornAddress = ""; 
	
	
	private boolean checkIfParenthesesAreBalanced() {
		int parenthesesCount = 0;
		boolean initialLeftParenthesisFlag = false;
		boolean unbalancedFlag = false;
		for(int i = 0; i < syntaxTree.length(); i++) {
			if(parenthesesCount < 0){
				unbalancedFlag = true;
				break;
			}
			else if(syntaxTree.charAt(i) == left){
				parenthesesCount++;
				initialLeftParenthesisFlag = true;
			}
			else if(syntaxTree.charAt(i) == right) {
				parenthesesCount--;
			}
		}
		if(unbalancedFlag == true || initialLeftParenthesisFlag == false || parenthesesCount != 0) {
			return false;
		} 
		else {
			return true;
		}
	}
	
	private String findGornAddressNonRecursive() {
		String subTree = syntaxTree;
		String[] splitGornAddress = gornAddress.split("\\.");
		int[] intSplitGornAddress = new int[splitGornAddress.length];
		for(int i = 0; i < splitGornAddress.length; i++) {
			intSplitGornAddress[i] = Integer.parseInt(splitGornAddress[i]);
		}
		
		String returnValue = "";
		String result = "";
		
		int balance = 0;
		int matches = 0;
		int addressCounter = 0;
		
		boolean firstParenthesisTag = false;
		
		
		for(int i = 0; i < intSplitGornAddress.length; i++) {
			int node = intSplitGornAddress[i];
			firstParenthesisTag = false;
			matches = 0;
			balance = 0;
			result = "";
		
			for(int j = 0; j < subTree.length(); j++){
				char syntaxChar = subTree.charAt(j);
				if(syntaxChar == left) {
					balance++;
				}
				else if(syntaxChar == right) {
					balance--;
				}
				if(syntaxChar == left) {
					firstParenthesisTag = true;
				}
				if (matches == node && firstParenthesisTag == true) {
					result = new StringBuilder(result).append(syntaxChar).toString();
					//System.out.println(result);
				}
				if(firstParenthesisTag == true && balance == 0 && syntaxChar == right) {
					if (matches == node) {
						subTree = result;
						
						subTree = subTree.replaceAll("^\\s+", "");
						subTree = subTree.replaceAll("\\s+$", "");
						subTree = subTree.replaceAll("^\\(|\\)$", "");
						
						break;
					}
					matches++;
				}
			}
			
			addressCounter++;
			if (addressCounter == intSplitGornAddress.length) {
				returnValue = subTree;
			}
		}
		return returnValue;
	}
	
	public static String[] readlines(String filename) throws IOException {
		FileReader fileReader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> lines = new ArrayList<String>();
		String line = null;
		while((line = bufferedReader.readLine()) != null) {
			lines.add(line);
		}
		bufferedReader.close();
		return lines.toArray(new String[lines.size()]);
	}
	
	public static void main(String[] args) {
		SyntaxString syntaxString = new SyntaxString();
		
		try {
			String[] fileContents = SyntaxString.readlines(args[0]);
			for (int i = 0; i < fileContents.length; i+=3) {
				syntaxString.syntaxTree = fileContents[i];
				syntaxString.gornAddress = fileContents[i+1];
				
				boolean parenthesesAreBalanced = syntaxString.checkIfParenthesesAreBalanced();
				if (parenthesesAreBalanced == false) {
					System.out.println("The parentheses are not balanced in : " + fileContents[i]);
				}
				
				String gornResult = syntaxString.findGornAddressNonRecursive();
				System.out.println(gornResult);
				System.out.println(fileContents[i+2]);
			}
		} catch (IOException e){
			System.err.println("Caught IOException: " + e.getMessage());
		}
	}
}
