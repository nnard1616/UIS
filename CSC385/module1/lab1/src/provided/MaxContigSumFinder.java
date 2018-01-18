package provided;

import java.util.*;

public class MaxContigSumFinder {

	private final static int RANGE = 10;

	private int numberRange;
	private int[] numberList;
	private int listSize;

	public MaxContigSumFinder() {
		numberRange = RANGE;
		listSize = 0;
	}
	
	public void init(int size)
	{	
		if (listSize != size)
		{
			listSize = size;
			numberList = new int[listSize];
			createNumberList();
		}
	}
	
	
	public void createNumberList() {
		int i;
		int j;
		Date d = new Date();
		Random r = new Random(d.getTime());
		
		for (i = 0; i < numberList.length; i++) {
			j = r.nextInt(numberRange);
			
			if (j < 3)
				numberList[i] = -j;
			else if (j < 5)
				numberList[i] = -(2 * j);
			else
				numberList[i] = j;
		}
	}
	
	
	public String cubicAlgorithmResults() {
		long maxSum = 0L;
		int seqStart = -1;
		int seqEnd = -1;
		Date d;
		long t1;
		long elapsedTime;

		d = new Date();
		t1 = d.getTime();
		
		for (int i = 0; i < numberList.length; i++)
			for (int j = i; j < numberList.length; j++) {

				long thisSum = 0L;

				for (int k = i; k <= j; k++)
					thisSum += numberList[k]; // dominant statement - determines the complexity

				if (thisSum > maxSum) {
					maxSum = thisSum;
					seqStart = i;
					seqEnd = j;
				}
			}

		d = new Date();
		elapsedTime = d.getTime() - t1;
		
		return ("Cubic algorithm took " + elapsedTime + " milliseconds.");
	}
	
	
	public String quadraticAlgorithmResults() {
		long maxSum = 0L;
		int seqStart = -1;
		int seqEnd = -1;
		Date d;
		long t1;
		long elapsedTime;

		d = new Date();
		t1 = d.getTime();
		
		for (int i = 0; i < numberList.length; i++) {
			
			long thisSum = 0L;
			
			for (int j = i; j < numberList.length; j++) {

				thisSum += numberList[j];

				if (thisSum > maxSum) {
					maxSum = thisSum;
					seqStart = i;
					seqEnd = j;
				}
			}
		}

		d = new Date();
		elapsedTime = d.getTime() - t1;
		
		return ("Quadratic algorithm took " + elapsedTime + " milliseconds.");
	}
	
	
	public String linearAlgorithmResults() {
		long maxSum = 0L;
		long thisSum = 0L;
		int seqStart = -1;
		int seqEnd = -1;
		Date d;
		long t1;
		long elapsedTime;

		d = new Date();
		t1 = d.getTime();

		for (int i = 0, j = 0; j < numberList.length; j++) {

			thisSum += numberList[j];

			if (thisSum > maxSum) {
				maxSum = thisSum;
				seqStart = i;
				seqEnd = j;

			}
			else if (thisSum < 0) {
				i = j + 1;
				thisSum = 0L;
			}
		}

		d = new Date();
		elapsedTime = d.getTime() - t1;
		
		return ("Linear algorithm took " + elapsedTime + " milliseconds.");
	}
	
}