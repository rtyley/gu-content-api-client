package com.madgag.guardian.guardian.spom.detection;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.madgag.guardian.guardian.NormalisedArticleProvider;
import com.madgag.guardian.guardian.spom.detection.reporting.SpomDetectionReporter;

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

	public SpomReport identifySpomsFor(String preferredMasterId, Collection<String> listOfPossibleSpomIds) {
		return identifySpomsFor(articleProvider.normalisedArticleFor(preferredMasterId), listOfPossibleSpomIds);
	}
	
	public SpomReport identifySpomsFor(NormalisedArticle preferredMaster, Collection<String> listOfPossibleSpomIds) {
		listOfPossibleSpomIds = newHashSet(listOfPossibleSpomIds);
		listOfPossibleSpomIds.remove(preferredMaster.getId());
		float thresholdScore = spomMatchScorer.getThresholdFor(preferredMaster);
		log.info("Processing masterArticle="+preferredMaster+" text len="+preferredMaster.getNormalisedBodyText().length()+" threshold="+thresholdScore+" candidates:"+listOfPossibleSpomIds.size());
		
		Map<NormalisedArticle,MatchScore> detectedSpoms=newHashMap();
		for (String possibleSpomId : listOfPossibleSpomIds) {
			NormalisedArticle possibleSpom=articleProvider.normalisedArticleFor(possibleSpomId);
			if (possibleSpom!=null) {
				MatchScore currentMatchScore = spomMatchScorer.getMatchScore(preferredMaster, possibleSpom, thresholdScore); 
				if (currentMatchScore!=null) {
					log.info("Found possible! "+currentMatchScore+" "+possibleSpomId);
					detectedSpoms.put(possibleSpom, currentMatchScore);
				}
			}
		}
		
		SpomReport spomReport = new SpomReport(preferredMaster,detectedSpoms);
		spomDetectionReporter.report(spomReport);
		return spomReport;
	}

}
