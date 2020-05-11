package com.diversityarrays.ds14db.SwaggerParser;

import java.util.Arrays;
import java.util.stream.IntStream;

public class GFG  
{ 
	// Method to allocate memory to blocks as per Best fit 
	// algorithm 
	static void bestFit(int blockSize[], int m, int processSize[],  
			int n) 
	{ 
		// Stores block id of the block allocated to a 
		// process 
		int allocation[] = new int[n]; 

		// Initially no block is assigned to any process 
		for (int i = 0; i < allocation.length; i++) 
			allocation[i] = -1; 

		// pick each process and find suitable blocks 
		// according to its size ad assign to it 
		for (int i=0; i<n; i++) 
		{ 
			// Find the best fit block for current process 
			int bestIdx = -1; 
			for (int j=0; j<m; j++) 
			{ 
				if (blockSize[j] >= processSize[i]) 
				{ 
					if (bestIdx == -1) 
						bestIdx = j; 
					else if (blockSize[bestIdx] > blockSize[j]) 
						bestIdx = j; 
				} 
			} 

			// If we could find a block for current process 
			if (bestIdx != -1) 
			{ 
				// allocate block j to p[i] process 
				allocation[i] = bestIdx; 

				// Reduce available memory in this block. 
				blockSize[bestIdx] -= processSize[i]; 
			} 
		} 

		System.out.println("\nProcess No.\tProcess Size\tBlock no."); 
		for (int i = 0; i < n; i++) 
		{ 
			System.out.print("   " + (i+1) + "\t\t" + processSize[i] + "\t\t"); 
			if (allocation[i] != -1) 
				System.out.print(allocation[i] + 1); 
			else
				System.out.print("Not Allocated"); 
			System.out.println(); 
		} 
		
		for (int i = 0; i < blockSize.length; i++) {
			final int  iV = i;
			int total = IntStream.range(0, allocation.length).filter(ii -> allocation[ii]==iV).map(ii -> processSize[ii]).sum();
			System.out.println("Total counts for "+(i+1) + ": "+ total);
		}
		
		
	} 

	// Driver Method 
	public static void main(String[] args) 
	{ 
		int totalTagCount = (int) (2500000 * 1.15);
		int blockSize[] = IntStream.range(0, 2).map(i -> totalTagCount).toArray();
		int processSize[] = {1926421	,1031940,	974723,	926908	, 924437}; 
		int m = blockSize.length; 
		int n = processSize.length; 

		bestFit(blockSize, m, processSize, n); 
	} 
} 