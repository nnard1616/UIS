package mcs;

public class MCS
{

	//-------------------------------------------------------------------------
	//	Constants
	//-------------------------------------------------------------------------

	//-------------------------------------------------------------------------
	//	Attributes
	//-------------------------------------------------------------------------
	
	String[] statementsCubic;
	int[] countsCubic;
	
	String[] statementsQuadratic;
	int[] countsQuadratic;
	
	String[] statementsLinear;
	int[] countsLinear;

	//-------------------------------------------------------------------------
	//	Constructors
	//-------------------------------------------------------------------------
	
	public MCS()
	{
		statementsCubic = new String[22];
		countsCubic = new int[22];
		
		statementsCubic[0] =	"      int maxSum = 0;";
		statementsCubic[1] =	"   int seqStart = -1;";
		statementsCubic[2] =	"     int seqEnd = -1;";
		statementsCubic[3] =	"           int i = 0;";
		statementsCubic[4] =	"  (outer) for ( ; ; )";
		statementsCubic[5] =	"    if (i < a.length)";
		statementsCubic[6] =	"           int j = i;";
		statementsCubic[7] =	" (middle) for ( ; ; )";
		statementsCubic[8] =	"    if (j < a.length)";
		statementsCubic[9] =	"     int thisSum = 0;";
		statementsCubic[10] =	"           int k = i;";
		statementsCubic[11] =	"  (inner) for ( ; ; )";
		statementsCubic[12] =	"          if (k <= j)";
		statementsCubic[13] =	"     thisSum += a[k];";
		statementsCubic[14] =	"if (thisSum > maxSum)";
		statementsCubic[15] =	"    maxSum = thisSum;";
		statementsCubic[16] =	"        seqStart = i;";
		statementsCubic[17] =	"          seqEnd = j;";
		statementsCubic[18] =	"                 k++;";
		statementsCubic[19] =	"                 j++;";
		statementsCubic[20] =	"                 i++;";
		statementsCubic[21] =	"       return maxSum;";
		
		statementsQuadratic = new String[18];
		countsQuadratic = new int[18];
		
		statementsQuadratic[0] =	"      int maxSum = 0;";
		statementsQuadratic[1] =	"   int seqStart = -1;";
		statementsQuadratic[2] =	"     int seqEnd = -1;";
		statementsQuadratic[3] =	"           int i = 0;";
		statementsQuadratic[4] =	"  (outer) for ( ; ; )";
		statementsQuadratic[5] =	"    if (i < a.length)";
		statementsQuadratic[6] =	"     int thisSum = 0;";
		statementsQuadratic[7] =	"           int j = i;";
		statementsQuadratic[8] =	"  (inner) for ( ; ; )";
		statementsQuadratic[9] =	"    if (j < a.length)";
		statementsQuadratic[10] =	"     thisSum += a[j];";
		statementsQuadratic[11] =	"if (thisSum > maxSum)";
		statementsQuadratic[12] =	"    maxSum = thisSum;";
		statementsQuadratic[13] =	"        seqStart = i;";
		statementsQuadratic[14] =	"          seqEnd = j;";
		statementsQuadratic[15] =	"                 j++;";
		statementsQuadratic[16] =	"                 i++;";
		statementsQuadratic[17] =	"       return maxSum;";
		
		statementsLinear = new String[18];
		countsLinear = new int[18];
		
		statementsLinear[0] =	"      int maxSum = 0;";
		statementsLinear[1] =	"     int thisSum = 0;";
		statementsLinear[2] =	"   int seqStart = -1;";
		statementsLinear[3] =	"     int seqEnd = -1;";
		statementsLinear[4] =	"           int i = 0;";
		statementsLinear[5] =	"           int j = 0;";
		statementsLinear[6] =	"          for ( ; ; )";
		statementsLinear[7] =	"    if (j < a.length)";
		statementsLinear[8] =	"     thisSum += a[j];";
		statementsLinear[9] =	"if (thisSum > maxSum)";
		statementsLinear[10] =	"    maxSum = thisSum;";
		statementsLinear[11] =	"        seqStart = i;";
		statementsLinear[12] =	"          seqEnd = j;";
		statementsLinear[13] =	"else if (thisSum < 0)";
		statementsLinear[14] =	"           i = j + 1;";
		statementsLinear[15] =	"         thisSum = 0;";
		statementsLinear[16] =	"                 j++;";
		statementsLinear[17] =	"       return maxSum;";

	}

	//-------------------------------------------------------------------------
	//	Methods
	//-------------------------------------------------------------------------
	
	
	public int mcsCubic (int[] a)
	{
		int maxSum = 0;
		int seqStart = -1; 
		int seqEnd = -1; 

		for (int i = 0; i < a.length; i++)
		{
			for (int j = i; j < a.length; j++)
			{
				int thisSum = 0;

				for (int k = i; k <= j; k++)
				{
					thisSum += a[k];
				}

				if (thisSum > maxSum)
				{
					maxSum = thisSum;
					seqStart = i;
					seqEnd = j;
				}
			}
		}

		return maxSum; 
	}
	
	
	public void analyzeMCSCubic(int[] sequence)
	{
		for (int i = 0; i < countsCubic.length; i++)
		{
			countsCubic[i] = 0;
		}
		
		int mcs = mcsCubicInstrumented(sequence);
		
		System.out.println("Analysis of Cubic MCSInstrumented Algorithm:\n");
		System.out.println("N = " + sequence.length);
		System.out.println("MCSInstrumented = " + mcs);
		System.out.println("\nStatement Counts:");
		
		for (int s = 0; s < statementsCubic.length; s++)
		{
			System.out.println(statementsCubic[s] + "    (" + countsCubic[s] + ")");
		}
		
		System.out.println();
		System.out.print("Total number of statement executions: ");
		
		int sum = 0;
		for (int t = 0; t < countsCubic.length; t++)
		{
			sum += countsCubic[t];
		}
		
		System.out.println("" + sum);
	}
	
	
	public void analyzeMCSQuadratic(int[] sequence)
	{
		for (int i = 0; i < countsQuadratic.length; i++)
		{
			countsQuadratic[i] = 0;
		}
		
		int mcs = mcsQuadraticInstrumented(sequence);
		
		System.out.println("Analysis of Quadratic MCSInstrumented Algorithm:\n");
		System.out.println("N = " + sequence.length);
		System.out.println("MCSInstrumented = " + mcs);
		System.out.println("\nStatement Counts:");
		
		for (int s = 0; s < statementsQuadratic.length; s++)
		{
			System.out.println(statementsQuadratic[s] + "    (" + countsQuadratic[s] + ")");
		}
		
		System.out.println();
		System.out.print("Total number of statement executions: ");
		
		int sum = 0;
		for (int t = 0; t < countsQuadratic.length; t++)
		{
			sum += countsQuadratic[t];
		}
		
		System.out.println("" + sum);
	}
	
	
	public void analyzeMCSLinear(int[] sequence)
	{
		for (int i = 0; i < countsLinear.length; i++)
		{
			countsLinear[i] = 0;
		}
		
		int mcs = mcsLinearInstrumented(sequence);
		
		System.out.println("Analysis of Linear MCSInstrumented Algorithm:\n");
		System.out.println("N = " + sequence.length);
		System.out.println("MCSInstrumented = " + mcs);
		System.out.println("\nStatement Counts:");
		
		for (int s = 0; s < statementsLinear.length; s++)
		{
			System.out.println(statementsLinear[s] + "    (" + countsLinear[s] + ")");
		}
		
		System.out.println();
		System.out.print("Total number of statement executions: ");
		
		int sum = 0;
		for (int t = 0; t < countsLinear.length; t++)
		{
			sum += countsLinear[t];
		}
		
		System.out.println("" + sum);
	}


	public int mcsCubicInstrumented (int[] a)
	{	
		int maxSum = 0;
		countsCubic[0]++;
		
		int seqStart = -1;
		countsCubic[1]++;
		
		int seqEnd = -1;
		countsCubic[2]++;
		
		int i = 0;
		countsCubic[3]++;

		countsCubic[4]++;
		for ( ; ; )
		{
			countsCubic[5]++;
			if (i < a.length)
			{
				int j = i;
				countsCubic[6]++;

				countsCubic[7]++;
				for ( ; ; )
				{
					countsCubic[8]++;
					if (j < a.length)
					{
						int thisSum = 0;
						countsCubic[9]++;

						int k = i;
						countsCubic[10]++;

						countsCubic[11]++;
						for ( ; ; )
						{
							countsCubic[12]++;
							if (k <= j) 
							{
								thisSum += a[k];
								countsCubic[13]++;
							}
							else
							{
								break;
							}

							k++;
							countsCubic[14]++;
						}

						countsCubic[15]++;
						if (thisSum > maxSum)
						{
							maxSum = thisSum;
							countsCubic[16]++;

							seqStart = i;
							countsCubic[17]++;

							seqEnd = j;
							countsCubic[18]++;
						}
					}
					else
					{
						break;
					}

					j++;
					countsCubic[19]++;
				}
				
			}
			else
			{
				break;
			}
			
			i++;
			countsCubic[20]++;
		}

		countsCubic[21]++;
		return maxSum; 
	}
	
	
	public int mcsQuadraticInstrumented (int[] a)
	{
		int maxSum = 0;
		countsQuadratic[0]++;
		
		int seqStart = -1;
		countsQuadratic[1]++;
		
		int seqEnd = -1;
		countsQuadratic[2]++;
		
		int i = 0;
		countsQuadratic[3]++;

		countsQuadratic[4]++;
		for ( ; ; )
		{
			countsQuadratic[5]++;
			if (i < a.length) 
			{
				int thisSum = 0;
				countsQuadratic[6]++;
				
				int j = i;
				countsQuadratic[7]++;

				countsQuadratic[8]++;
				for ( ; ; )
				{
					countsQuadratic[9]++;
					if (j < a.length)
					{
						thisSum += a[j];
						countsQuadratic[10]++;
						
						countsQuadratic[11]++;
						if (thisSum > maxSum)
						{
							maxSum = thisSum;
							countsQuadratic[12]++;
							
							seqStart = i;
							countsQuadratic[13]++;
							
							seqEnd = j;
							countsQuadratic[14]++;
						}
					}
					else
					{
						break;
					}

					countsQuadratic[15]++;
					j++;

				}

				countsQuadratic[16]++;
				i++;

			}
			else
			{
				break;
			}
		}

		countsQuadratic[17]++;
		return maxSum; 
	}
	
	
	public int mcsLinearInstrumented (int[] a)
	{
		int maxSum = 0;
		countsLinear[0]++;

		int thisSum = 0; 
		countsLinear[1]++;

		int seqStart = -1; 
		countsLinear[2]++;

		int seqEnd = -1; 
		countsLinear[3]++;

		int i = 0;
		countsLinear[4]++;

		int j = 0;
		countsLinear[5]++;

		countsLinear[6]++;
		for ( ; ; )
		{
			countsLinear[7]++;
			if (j < a.length)
			{
				thisSum += a[j];
				countsLinear[8]++;

				countsLinear[9]++;
				if (thisSum > maxSum)
				{
					maxSum = thisSum;
					countsLinear[10]++;

					seqStart = i;
					countsLinear[11]++;

					seqEnd = j;
					countsLinear[12]++;
				}
				else if ((countsLinear[13]++ > -1) && thisSum < 0) 
				{
					i = j + 1; 
					countsLinear[14]++;
					
					thisSum = 0;
					countsLinear[15]++;
				} 
			}
			else
			{
				break;
			}

			j++;
			countsLinear[16]++;
		}

		countsLinear[17]++;
		return maxSum;
	}
	
	
	public void testLoopsWithRepetition(int N)
	{
		int aCount = 0;
		int bCount = 0;
		int cCount = 0;
		
		int a = 0;
		
		for ( ; ; )
		{
			if (a < N)
			{
				aCount++;
				
				int b = 0;
				
				for ( ; ; )
				{
					if (b < N)
					{
						bCount++;
						
						int c = 0;
						
						for ( ; ; )
						{
							if (c < N)
							{
								cCount++;
							}
							else
							{
								break;
							}
							
							c++;
						}
					}
					else
					{
						break;
					}
					
					b++;
				}
			}
			else
			{
				break;
			}
			a++;
		}
		
		System.out.println("aCount: " + aCount);
		System.out.println("bCount: " + bCount);
		System.out.println("cCount: " + cCount);
	}
}
