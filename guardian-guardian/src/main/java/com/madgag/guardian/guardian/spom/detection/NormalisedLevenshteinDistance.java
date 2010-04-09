package com.madgag.guardian.guardian.spom.detection;

public class NormalisedLevenshteinDistance {

	private final float value;
	
	public NormalisedLevenshteinDistance(int levenshteinDistance, int originalTextLength) {
		value = (float) levenshteinDistance / originalTextLength;
	}
	
	public float getValue() {
		return value;
	}
	
}
