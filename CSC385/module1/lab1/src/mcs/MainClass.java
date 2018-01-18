package mcs;

public class MainClass
{

	/**
	 *	
	 *
	 *	@param args
	 */
	public static void main(String[] args)
	{
		MCS mcs = new MCS();
		
		int[] sequence1 = {-2, -3, 4, -1, 4, 2, 3};
		
		int[] sequence2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		
		int[] sequence3 = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
		
		int[] sequence4 = {1, 2, 3, 4, 0, 6, 7, 8, 9, 10};
		
		int[] sequence5 = {1, 2, 3, 4, 5, 6, 7, 8};
		
		int[] sequence6 = {1, 2, 3, 4, 5};
		
		int[] sequence7 = new int[10000];
		for (int i = 0; i < sequence7.length; i++)
		{
			sequence7[i] = i + 1;
		}
		
		int[] sequence8 = {-1, -2, -3, -4, -5, -6, -7, -8, -9, -10};
		
		int i = 0;
		/*
		System.out.print("MCSInstrumented(instrumented) for [");
		for (i = 0; i < sequence1.length - 1; i++)
		{
			System.out.print(sequence1[i] + ", ");
		}
		System.out.println(sequence1[i] + "]:\n");
		mcs.analyzeMCSCubic(sequence1);
		System.out.println();
		*/
		/*
		System.out.print("MCSInstrumented(instrumented) for [");
		for (i = 0; i < sequence6.length - 1; i++)
		{
			System.out.print(sequence6[i] + ", ");
		}
		System.out.println(sequence6[i] + "]:\n");
		mcs.analyzeMCSCubic(sequence6);
		System.out.println();
		
		System.out.print("MCSInstrumented(instrumented) for [");
		for (i = 0; i < sequence2.length - 1; i++)
		{
			System.out.print(sequence2[i] + ", ");
		}
		System.out.println(sequence2[i] + "]:\n");
		mcs.analyzeMCSCubic(sequence2);
		System.out.println();
		/*
		System.out.print("MCSInstrumented(instrumented) for [");
		for (i = 0; i < sequence3.length - 1; i++)
		{
			System.out.print(sequence3[i] + ", ");
		}
		System.out.println(sequence3[i] + "]:\n");
		mcs.analyzeMCSCubic(sequence3);
		System.out.println();
		
		System.out.print("MCSInstrumented(instrumented) for [");
		for (i = 0; i < sequence4.length - 1; i++)
		{
			System.out.print(sequence4[i] + ", ");
		}
		System.out.println(sequence4[i] + "]:\n");
		mcs.analyzeMCSCubic(sequence4);
		System.out.println();
		*/
		/*
		System.out.print("MCSInstrumented(instrumented) for [");
		for (i = 0; i < sequence5.length - 1; i++)
		{
			System.out.print(sequence5[i] + ", ");
		}
		System.out.println(sequence5[i] + "]:\n");
		mcs.analyzeMCSCubic(sequence5);
		System.out.println();
		
		System.out.print("MCSInstrumented(instrumented) for [");
		for (i = 0; i < sequence7.length - 1; i++)
		{
			System.out.print(sequence7[i] + ", ");
		}
		System.out.println(sequence7[i] + "]:\n");
		mcs.analyzeMCSCubic(sequence7);
		System.out.println();
		
		System.out.print("MCSInstrumented(instrumented) for [");
		for (i = 0; i < sequence8.length - 1; i++)
		{
			System.out.print(sequence8[i] + ", ");
		}
		System.out.println(sequence8[i] + "]:\n");
		mcs.analyzeMCSCubic(sequence8);
		System.out.println();
		*/
		/*
		System.out.print("MCSQuadratic(instrumented) for [");
		for (i = 0; i < sequence2.length - 1; i++)
		{
			System.out.print(sequence2[i] + ", ");
		}
		System.out.println(sequence2[i] + "]:\n");
		mcs.analyzeMCSQuadratic(sequence2);
		System.out.println();
		*/
		/*
		System.out.print("MCSLinear(instrumented) for [");
		for (i = 0; i < sequence1.length - 1; i++)
		{
			System.out.print(sequence1[i] + ", ");
		}
		System.out.println(sequence1[i] + "]:\n");
		mcs.analyzeMCSLinear(sequence1);
		System.out.println();
		
		System.out.print("MCSLinear(instrumented) for [");
		for (i = 0; i < sequence2.length - 1; i++)
		{
			System.out.print(sequence2[i] + ", ");
		}
		System.out.println(sequence2[i] + "]:\n");
		mcs.analyzeMCSLinear(sequence2);
		System.out.println();
		*/
		/*
		System.out.println("Testing 3 nested loops with repetition:");
		mcs.testLoopsWithRepetition(10);
		*/
		
		System.out.print("Comparison of performances for input sequence of 10,000 positive integers:\n");
		
		System.out.print("MCSCubic:");
		mcs.analyzeMCSCubic(sequence2);
		System.out.println();
		
		System.out.print("MCSQuadratic:");
		mcs.analyzeMCSQuadratic(sequence2);
		System.out.println();
		
		System.out.print("MCSLinear:");
		mcs.analyzeMCSLinear(sequence2);
		System.out.println();
	}

	//-------------------------------------------------------------------------
	//	Constants
	//-------------------------------------------------------------------------

	//-------------------------------------------------------------------------
	//	Attributes
	//-------------------------------------------------------------------------

	//-------------------------------------------------------------------------
	//	Constructors
	//-------------------------------------------------------------------------

	//-------------------------------------------------------------------------
	//	Methods
	//-------------------------------------------------------------------------
}
