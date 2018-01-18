package mcs.instrumented;

public class MainClass
{

	/**
	 *	
	 *
	 *	@param args
	 */
	public static void main(String[] args)
	{
		MCSInstrumented mCSInstrumented = new MCSInstrumented();
		
		// some test sequences
		
		// sequence mentioned in lecture
		int[] sequence1 = {-2, -3, 4, -1, 4, 2, 3};
		
		// simple sequence of all positive integers--represents worst case of size N = 10
		int[] sequence2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		
		// another worst case scenario of size N = 10
		int[] sequence3 = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
		
		// worst case scenario of size N = 8
		int[] sequence5 = {1, 2, 3, 4, 5, 6, 7, 8};
		
		// worst case scenario of size N = 5
		int[] sequence6 = {1, 2, 3, 4, 5};
		
		// large worst case scenario; N = 10,000
		int[] sequence7 = new int[10000];
		for (int i = 0; i < sequence7.length; i++)
		{
			sequence7[i] = i + 1;
		}
		
		// slightly better than worst case: the 0 in the sequence affects the number of times
		// maxSum must be swapped with thisSum 
		int[] sequence4 = {1, 2, 3, 4, 0, 6, 7, 8, 9, 10};
		
		// all negative integers: current sum will always decrease
		int[] sequence8 = {-1, -2, -3, -4, -5, -6, -7, -8, -9, -10};
		
		int i = 0;

		// compare all 3 algorithms; cubic algorithm will take a LONG time if N is large
		// change the sequence used to look at different problem sizes and different scenarios
		System.out.print("Comparison of performances of cubic, quadratic, and linear MCS algorithms:\n\n");
		
		System.out.print("MCSCubic:");
		mCSInstrumented.analyzeMCSCubic(sequence2);
		System.out.println();
		
		System.out.print("MCSQuadratic:");
		mCSInstrumented.analyzeMCSQuadratic(sequence2);
		System.out.println();
		
		System.out.print("MCSLinear:");
		mCSInstrumented.analyzeMCSLinear(sequence2);
		System.out.println();
	}

}
