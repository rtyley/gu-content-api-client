package com.madgag.guardian.guardian.spom.detection;

import static java.lang.Math.expm1;

import java.util.Set;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.madgag.guardian.guardian.NormalisedArticleProvider;

public class SpomIdentifier {

	private static final Logger log = Logger.getLogger(SpomIdentifier.class.getName());
	
	private final SpomMatchScorer spomMatchScorer;

	private final NormalisedArticleProvider articleProvider;

	@Inject
	public SpomIdentifier(SpomMatchScorer spomMatchScorer, NormalisedArticleProvider articleProvider) {
		this.spomMatchScorer = spomMatchScorer;
		this.articleProvider = articleProvider;
	}

	public DetectedSpom identifySpomsFor(NormalisedArticle preferredMaster,	Set<String> listOfPossibleSpomIds) {
		float bestMatchScore = getThresholdFor(preferredMaster.getNormalisedBodyText().length());
		log.info("Processing masterArticle="+preferredMaster+" text len="+preferredMaster.getNormalisedBodyText().length()+" threshold="+bestMatchScore);
		NormalisedArticle bestMatchedSpom = null;
		for (String possibleSpomId : listOfPossibleSpomIds) {
			NormalisedArticle possibleSpom=articleProvider.normalisedArticleFor(possibleSpomId);
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
