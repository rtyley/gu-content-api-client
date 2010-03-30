package com.madgag.guardian.guardian.spom.detection;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Collection;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.madgag.guardian.guardian.NormalisedArticleProvider;

public class SpomIdentifier {

	private static final Logger log = Logger.getLogger(SpomIdentifier.class.getName());
	
	private final SpomMatchScorer spomMatchScorer;

	private final NormalisedArticleProvider articleProvider;

	private final SpomDetectionReporter spomDetectionReporter;


	@Inject
	public SpomIdentifier(SpomMatchScorer spomMatchScorer, NormalisedArticleProvider articleProvider, SpomDetectionReporter spomDetectionReporter) {
		this.spomMatchScorer = spomMatchScorer;
		this.articleProvider = articleProvider;
		this.spomDetectionReporter = spomDetectionReporter;
	}

	public DetectedSpom identifySpomsFor(NormalisedArticle preferredMaster,	Collection<String> listOfPossibleSpomIds) {
		listOfPossibleSpomIds = newHashSet(listOfPossibleSpomIds);
		listOfPossibleSpomIds.remove(preferredMaster.getId());
		float bestMatchScore = spomMatchScorer.getThresholdFor(preferredMaster);
		log.info("Processing masterArticle="+preferredMaster+" text len="+preferredMaster.getNormalisedBodyText().length()+" threshold="+bestMatchScore+" candidates:"+listOfPossibleSpomIds.size());
		NormalisedArticle bestMatchedSpom = null;
		for (String possibleSpomId : listOfPossibleSpomIds) {
			NormalisedArticle possibleSpom=articleProvider.normalisedArticleFor(possibleSpomId);
			if (possibleSpom!=null) {
				float currentMatchScore = spomMatchScorer.getMatchScore(preferredMaster, possibleSpom, bestMatchScore); 
				if (currentMatchScore < bestMatchScore ) {
					log.info("Found possible! "+currentMatchScore+" "+possibleSpomId);
					spomDetectionReporter.reportStuff(preferredMaster, possibleSpom, currentMatchScore);
					bestMatchScore = currentMatchScore;
					bestMatchedSpom = possibleSpom;
				}
			}
		}
		if (bestMatchedSpom==null) {
			return null;
		}
		DetectedSpom detectedSpom = new DetectedSpom(preferredMaster, bestMatchedSpom);
		log.info("Found SPOM! "+detectedSpom);
		return detectedSpom;
	}

}
