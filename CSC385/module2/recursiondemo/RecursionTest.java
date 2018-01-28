import java.util.*;

public class RecursionTest {
	
	private long[] knownFib;
	
	public RecursionTest() {}
	
	
	public String reverseStringLoop(String s) {
		String rev = "";
		
		if (s.length() > 0)
			for (int i = s.length(); i > 0; i--)
				rev = rev + s.substring(i - 1, i);
		
		return rev;
	}
	
	public String reverseString(String s) {
		if (s.length() < 1)
			return "";
		else
			return reverse(s);
	}
	
	private String reverse(String s) {
		if (s.length() == 1)
			return s;
		else
			return reverse(tail(s)) + head(s);
	}
	
	private String head(String s) {
		return s.substring(0,1);
	}
	
	private String tail(String s) {
		return s.substring(1);
	}
	
	
	public String recursiveFibResults(int n) {
		long elapsedTime = 0L;
		Date d;
		long t1;
		long fib;
		
		d = new Date();
		t1 = d.getTime();
		
		fib = fibRecursive(n);
		
		d = new Date();
		elapsedTime = d.getTime() - t1;
		
		return "" + n + "th Fibonacci number = " + fib + "\nelapsed time of recursive algorithm: " + elapsedTime + " ms";
	}
	
	
	public String dynamicRecursiveFibResults(int n) {
		long elapsedTime = 0L;
		Date d;
		long t1;
		long fib;
		
		knownFib = new long[n + 1];
		
		d = new Date();
		t1 = d.getTime();
		
		fib = dynamicFibRecursive(n);
		
		d = new Date();
		elapsedTime = d.getTime() - t1;
		
		return "" + n + "th Fibonacci number = " + fib + "\nelapsed time of dynamic recursive algorithm: " + elapsedTime + " ms";
	}
	
	
	public String linearFibResults(int n) {
		long elapsedTime = 0L;
		Date d;
		long t1;
		long fib;
		
		d = new Date();
		t1 = d.getTime();
		
		fib = fibLinear(n);
		
		d = new Date();
		elapsedTime = d.getTime() - t1;
		
		return "" + n + "th Fibonacci number = " + fib + "\nelapsed time of linear algorithm: " + elapsedTime + " ms";
	}
	
	
	public long fibLinear(int n) {
		long nMinusTwo = 0L;
		long nMinusOne = 1L;
		long sum = 1L;
		int i;

		if (n == 0)
			sum = n;
		else
			for (i = 2; i <= n; i++) {
				sum = nMinusTwo + nMinusOne;
				nMinusTwo = nMinusOne;
				nMinusOne = sum;
			}


		return sum;
	}

	
	public long dynamicFibRecursive(int n) {
		if (knownFib[n] != 0)
			return knownFib[n];
			
		long sum = n;
		if (n < 0)
			return 0;
		
		if (n > 1)
			sum = dynamicFibRecursive(n - 1) + dynamicFibRecursive(n - 2);
			
		knownFib[n] = sum;
		
		return knownFib[n];
	}
	
	
	public long fibRecursive(int n) {
		if (n <= 1)
			return n;
		else
			return fibRecursive(n - 1) + fibRecursive(n - 2);
	}
	
	
	public long gcd(long a, long b) {
		if (b == 0)
			return a;
		else
			return gcd(b, a % b);
	}
	

}