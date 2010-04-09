package com.madgag.guardian.guardian.spom.detection;

public class MatchScore implements Comparable<MatchScore> {
	
	public static final MatchScore DISQUALIFIED = new MatchScore(null, Float.MAX_VALUE);

	public static final MatchScore OUTSIDE_THRESHOLD =  new MatchScore(null, Float.MAX_VALUE);
	
	private final NormalisedLevenshteinDistance normalisedLevenshteinDistance;
	private final float weighting;

	public MatchScore(NormalisedLevenshteinDistance normalisedLevenshteinDistance, float weighting) {
		this.normalisedLevenshteinDistance = normalisedLevenshteinDistance;
		this.weighting = weighting;
	}

	public NormalisedLevenshteinDistance getNormalisedLevenshteinDistance() {
		return normalisedLevenshteinDistance;
	}

	private float getValue() {
		return normalisedLevenshteinDistance.getValue()+weighting;
	}
	
	@Override
	public int compareTo(MatchScore other) {
		return Float.compare(this.getValue(),other.getValue());
	}

	public boolean isBetterThan(MatchScore otherMatchScore) {
		return compareTo(otherMatchScore)<0;
	}
}
