package com.madgag.guardian.guardian.spom.detection;

import static com.google.common.collect.ImmutableSet.of;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.List;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.madgag.guardian.guardian.content.BatchingNormalisedArticleProvider;
import com.madgag.guardian.guardian.spom.detection.reporting.SpomDetectionReporter;

public class SpomIdentifier {

	private static final Logger log = Logger.getLogger(SpomIdentifier.class.getName());
	
	private final SpomMatchScorer spomMatchScorer;
	
	private final BatchingNormalisedArticleProvider articleProvider;

	private final SpomDetectionReporter spomDetectionReporter;


	@Inject
	public SpomIdentifier(SpomMatchScorer spomMatchScorer,BatchingNormalisedArticleProvider articleProvider, SpomDetectionReporter spomDetectionReporter) {
		this.spomMatchScorer = spomMatchScorer;
		this.articleProvider = articleProvider;
		this.spomDetectionReporter = spomDetectionReporter;
	}
	
	public SpomReport identifySpomsFor(String targetId, Iterable<String> possibleSpomIds) {
		Iterable<NormalisedArticle> possibleSpoms=articleProvider.normalisedArticlesFor(newHashSet(possibleSpomIds));
		NormalisedArticle preferredMaster = articleProvider.normalisedArticlesFor(of(targetId)).iterator().next();
		return identifySpomsFor(preferredMaster, possibleSpoms);
	}
	
	public SpomReport identifySpomsFor(NormalisedArticle preferredMaster, Iterable<NormalisedArticle> possibleSpoms) {
		float thresholdScore = spomMatchScorer.getThresholdFor(preferredMaster);
		log.fine("Processing masterArticle="+preferredMaster+" text len="+preferredMaster.getNormalisedBodyText().length()+" threshold="+thresholdScore);
		
		int numPossibleSpomsChecked=0;
		List<SpomMatch> detectedSpoms=newArrayList();
		for (NormalisedArticle possibleSpom : possibleSpoms) {
			if (possibleSpom!=null && !possibleSpom.getId().equals(preferredMaster.getId())) {
				MatchScore currentMatchScore = spomMatchScorer.getMatchScore(preferredMaster, possibleSpom, thresholdScore);
				numPossibleSpomsChecked++;
				if (currentMatchScore!=null) {
					log.info("Found possible! "+currentMatchScore+" "+possibleSpom);
					detectedSpoms.add(new SpomMatch(possibleSpom, currentMatchScore));
				}
			}
		}
		SpomReport spomReport = new SpomReport(preferredMaster,detectedSpoms);
		log.fine("Processing masterArticle="+preferredMaster+" - numPossibleSpomsChecked: "+numPossibleSpomsChecked+" report:"+spomReport);
		spomDetectionReporter.report(spomReport);
		return spomReport;
	}

}
