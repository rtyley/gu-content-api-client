package com.madgag.spom.util;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class LevenshteinWithDistanceThreshold {

	public int get(String s1, String s2, int threshold) {
		if (s1 == null || s2 == null) {
			throw new IllegalArgumentException("Strings must not be null");
		}

		if (s1.length()>s2.length()) {
			String swap=s2;s2=s1;s1=swap;
		}
		// s1 is shorter than s2
		if (s1.length() == 0) {
			return s2.length();
		}
		
		if (s2.length()-s1.length()>=threshold) {
			return Integer.MAX_VALUE;
		}
		
		threshold = min(s2.length()+1,threshold);

		int cost[] = new int[3+(2*threshold)]; // cost array, horizontally
		int previousCost[] = new int[cost.length]; // 'previous' cost array, horizontally
		
		// cost[0] = the 'border' cell we use to avoid AIOOBException
		cost[0] = cost[cost.length-1] = previousCost[0] = previousCost[cost.length-1] = Integer.MAX_VALUE;
		
		int numberOfCellsUpToAndIncMainDiagonal = threshold+2;
		
		for (int s1Index = 0; s1Index <= previousCost.length - numberOfCellsUpToAndIncMainDiagonal; ++s1Index) {
			previousCost[s1Index+threshold+1] = s1Index;
		}
		
		// The algorithm is roughly O(s1.length()*threshold)
		for (int s2SubstringLength = 1; s2SubstringLength <= s2.length(); ++s2SubstringLength) {
			char s2SubstringLastCharacter = s2.charAt(s2SubstringLength - 1);
			int numberOfCellsWithNonPositiveIndex = numberOfCellsUpToAndIncMainDiagonal - s2SubstringLength;			
			
			if (numberOfCellsWithNonPositiveIndex>0) {
				/*  left wall cell - set to equal the edit distance for 'empty string' to the full substring,
				 *  ie. the full length of the the substring
				 */
				cost[numberOfCellsWithNonPositiveIndex-1] = s2SubstringLength;
			}
		
			// cost[threshold+1] = cell on main diagonal
			// cost[1] = left-most cell we ever want to calculate
		
			int startIndex = max(1, numberOfCellsWithNonPositiveIndex);
			int endIndex = min(cost.length - 1, numberOfCellsWithNonPositiveIndex + s1.length());
			
			// These two variables are motivated purely by optimisation
			int cellCost = cost[startIndex - 1], costFromPrevRow = previousCost[startIndex];
				
			boolean rowHasNoValueUnderThreshold = true;
			
			for (int i = startIndex; i < endIndex; ++i) {
				char s1SubstringLastCharacter = s1.charAt(i - numberOfCellsWithNonPositiveIndex);
				int substitutionCost = s1SubstringLastCharacter == s2SubstringLastCharacter ? 0 : 1;
				int costUsingSubstitution = costFromPrevRow + substitutionCost;
				
				costFromPrevRow = previousCost[i+1]; // Note this variable gets updated here, is also used before
				int minCostUsingDeletionOrInsertion = 1+min(cellCost, costFromPrevRow);
				
				cost[i] = cellCost = min(minCostUsingDeletionOrInsertion, costUsingSubstitution);
				rowHasNoValueUnderThreshold &= cellCost >= threshold;
			}
			if (rowHasNoValueUnderThreshold) {
				return Integer.MAX_VALUE;
			}
			// copy current distance counts to 'previous row' distance counts
			int swapArray[] = previousCost;
			previousCost = cost;
			cost = swapArray;
		}
		
		// our last action in the above loop was to switch d and p, so p now
		// actually has the most recent cost counts
		int distance = previousCost[s1.length() + threshold + 1 - s2.length()];
		return (distance<threshold)?distance:Integer.MAX_VALUE;
	}

}
