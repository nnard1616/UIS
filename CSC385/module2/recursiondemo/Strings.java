public class Strings {
	
	public Strings() {}
	
	// return the reverse of s
	public String reverseString(String s) {
		return reverse(s);
	}
	
	// recursive helper method
	private String reverse(String s) {
		if (s.length() == 1)	//base case
			return s;
		else					//recursive case
			return reverse(tail(s)) + head(s);
	}
	
	//return the first character of s
	private String head(String s) {
		return s.substring(0,1);
	}
	
	//return all characters to the right of the first character
	private String tail(String s) {
		return s.substring(1);
	}
	

}