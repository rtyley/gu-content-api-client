package com.madgag.spom.detection;

import static java.lang.Math.expm1;

import java.util.List;
import java.util.logging.Logger;

public class SpomIdentifier {

	private static final Logger log = Logger.getLogger(SpomIdentifier.class.getName());
	
	private final SpomMatchScorer spomMatchScorer;

	public SpomIdentifier(SpomMatchScorer spomMatchScorer) {
		this.spomMatchScorer = spomMatchScorer;
	}

	public DetectedSpom identifySpomsFor(NormalisedArticle preferredMaster,	List<NormalisedArticle> listOfPossibleSpoms) {
		float bestMatchScore = getThresholdFor(preferredMaster.getNormalisedBodyText().length());
		log.info("Processing masterArticle="+preferredMaster+" text len="+preferredMaster.getNormalisedBodyText().length()+" threshold="+bestMatchScore);
		NormalisedArticle bestMatchedSpom = null;
		for (NormalisedArticle possibleSpom : listOfPossibleSpoms) {
			float currentMatchScore = spomMatchScorer.getMatchScore(preferredMaster, possibleSpom, bestMatchScore); 
			if (currentMatchScore < bestMatchScore ) {
				bestMatchScore = currentMatchScore;
				bestMatchedSpom = possibleSpom;
			}
		}
		if (bestMatchedSpom==null) {
			return null;
		}
		DetectedSpom detectedSpom = new DetectedSpom(preferredMaster, bestMatchedSpom);
		log.info("Found SPOM! "+detectedSpom);
		return detectedSpom;
	}
	
	float getThresholdFor(int textLength) {
		return (float) (0.25*(-expm1(-(textLength/200.0f))));
	}

}
