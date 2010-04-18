package com.madgag.guardian.guardian.spom.detection;

public class SpomMatch {
	private final NormalisedArticle spom;
	private final MatchScore matchScore;

	public SpomMatch(NormalisedArticle spom,MatchScore matchScore) {
		this.spom = spom;
		this.matchScore = matchScore;
	}
	
	public NormalisedArticle getSpom() {
		return spom;
	}
	
	public MatchScore getMatchScore() {
		return matchScore;
	}
}
