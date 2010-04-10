package com.madgag.guardian.guardian.spom.detection;

import java.util.Map;

public class SpomReport {
	
	private NormalisedArticle targetArticle;
	private Map<NormalisedArticle,MatchScore> detectedSpoms;
	
	public SpomReport(NormalisedArticle targetArticle, Map<NormalisedArticle, MatchScore> detectedSpoms) {
		this.targetArticle = targetArticle;
		this.detectedSpoms = detectedSpoms;
		
	}

	public NormalisedArticle getTargetArticle() {
		return targetArticle;
	}
	
	public Map<NormalisedArticle,MatchScore> getSpomsWithMatchScores() {
		return detectedSpoms;
	}

	public boolean hasDetectedSpoms() {
		return !detectedSpoms.isEmpty();
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+"["+targetArticle+" - "+detectedSpoms+"]";
	}

}
