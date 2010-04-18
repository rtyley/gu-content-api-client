package com.madgag.guardian.guardian.spom.detection;

import static java.lang.Math.round;

public class NormalisedLevenshteinDistance {

	private final float value;
	
	public NormalisedLevenshteinDistance(int levenshteinDistance, int originalTextLength) {
		value = (float) levenshteinDistance / originalTextLength;
	}
	
	public float getValue() {
		return value;
	}

	
	@Override
	public String toString() {
		return "\u0394="+round(value * 100)+"%";
	}
}
