package com.madgag.guardian.guardian.spom.detection;

import static java.lang.Math.expm1;
import static java.lang.Math.round;
import static java.util.logging.Level.FINE;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.madgag.text.util.LevenshteinWithDistanceThreshold;

public class SpomMatchScorer {

	private static final Logger log = Logger.getLogger(SpomMatchScorer.class.getName());
	
	private final LevenshteinWithDistanceThreshold levenshteinWithDistanceThreshold;

	/*
	 * This is equivalent to saying that an article with differing contributors
	 * has to match at least 5% more text of the preferred master
	 */
	public static final float CONTRIBUTOR_DOES_NOT_MATCH_WEIGHTING = 0.07f;

	@Inject
	public SpomMatchScorer(
			LevenshteinWithDistanceThreshold levenshteinWithDistanceThreshold) {
		this.levenshteinWithDistanceThreshold = levenshteinWithDistanceThreshold;
	}

	public float getMatchScore(NormalisedArticle preferredMaster,
			NormalisedArticle possibleSpom, float minimumSuccessfulScore) {
		if (log.isLoggable(FINE)) {
			log.fine("Comparing "+preferredMaster.getId()+" & "+possibleSpom.getId());
		}
		
		String preferredMasterBodyText = preferredMaster
				.getNormalisedBodyText();
		String possibleSpomBodyText = possibleSpom.getNormalisedBodyText();

		Set<String> matchingContributors = new HashSet<String>(preferredMaster
				.getContributorIds());
		matchingContributors.retainAll(possibleSpom.getContributorIds());

		boolean hasMatchingContributors = !matchingContributors.isEmpty();
		float contributorWeighting = getContributorWeighting(hasMatchingContributors);

		int requiredLevenshteinDistanceThreshold = round((minimumSuccessfulScore - contributorWeighting)
				* preferredMasterBodyText.length());

		int levenDistance = levenshteinWithDistanceThreshold.get(
				preferredMasterBodyText, possibleSpomBodyText,
				requiredLevenshteinDistanceThreshold);
		if (levenDistance > requiredLevenshteinDistanceThreshold) {
			return Float.MAX_VALUE;
		}
		return ((float) levenDistance / preferredMasterBodyText.length())
				+ contributorWeighting;
	}

	private float getContributorWeighting(boolean hasMatchingContributors) {
		return hasMatchingContributors ? 0f
				: CONTRIBUTOR_DOES_NOT_MATCH_WEIGHTING;
	}

	public float getThresholdFor(NormalisedArticle preferredMaster) {
		return (float) (0.25 * (-expm1(-(preferredMaster.getNormalisedBodyText().length() / 200.0f))));
	}

}
