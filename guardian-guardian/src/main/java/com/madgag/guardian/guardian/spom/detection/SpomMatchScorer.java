package com.madgag.guardian.guardian.spom.detection;

import static com.google.common.collect.Sets.intersection;
import static com.google.common.collect.Sets.newHashSet;
import static com.madgag.guardian.guardian.spom.detection.MatchScore.DISQUALIFIED;
import static com.madgag.guardian.guardian.spom.detection.MatchScore.OUTSIDE_THRESHOLD;
import static java.lang.Math.expm1;
import static java.lang.Math.round;
import static java.util.logging.Level.FINE;
import static org.joda.time.Duration.standardHours;

import java.util.Set;
import java.util.logging.Logger;

import org.joda.time.Duration;

import com.google.inject.Inject;
import com.madgag.guardian.contentapi.jaxb.Tag;
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

	public MatchScore getMatchScore(NormalisedArticle preferredMaster,
			NormalisedArticle possibleSpom, float minimumSuccessfulScore) {
		if (log.isLoggable(FINE)) {
			log.fine("Comparing "+preferredMaster.getId()+" & "+possibleSpom.getId());
		}
		
		
		Set<Tag> commonSeries = commonTags("series",preferredMaster, possibleSpom);
		Duration pubTimeDelta = new Duration(possibleSpom.getWebPublicationDate(), preferredMaster.getWebPublicationDate());
		//log.info(commonSeries+" "+duration.toString());
		if (!commonSeries.isEmpty()) {
			if (pubTimeDelta.isLongerThan(standardHours(12)) && preferredMaster.getNormalisedBodyText().length()<2000) {
				// let off boring repeated series...
				return null;
			}
		}
		
		String preferredMasterBodyText = preferredMaster.getNormalisedBodyText();
		String possibleSpomBodyText = possibleSpom.getNormalisedBodyText();

		Set<Tag> matchingContributors = commonTags("contributor",preferredMaster, possibleSpom);

		boolean hasMatchingContributors = !matchingContributors.isEmpty();
		float contributorWeighting = getContributorWeighting(hasMatchingContributors);

		int requiredLevenshteinDistanceThreshold = round((minimumSuccessfulScore - contributorWeighting)
				* preferredMasterBodyText.length());

		int levenDistance = levenshteinWithDistanceThreshold.get(
				preferredMasterBodyText, possibleSpomBodyText,
				requiredLevenshteinDistanceThreshold);
		if (levenDistance > requiredLevenshteinDistanceThreshold) {
			return null;
		}
		return new MatchScore(new NormalisedLevenshteinDistance(levenDistance, preferredMasterBodyText.length()), contributorWeighting);
	}

	private Set<Tag> commonTags(String tagType,NormalisedArticle preferredMaster, NormalisedArticle possibleSpom) {
		return intersection(
				newHashSet(preferredMaster.getTags().get(tagType)),
				newHashSet(possibleSpom.getTags().get(tagType)));
	}

	private float getContributorWeighting(boolean hasMatchingContributors) {
		return hasMatchingContributors ? 0f
				: CONTRIBUTOR_DOES_NOT_MATCH_WEIGHTING;
	}

	public float getThresholdFor(NormalisedArticle preferredMaster) {
		return (float) (0.25 * (-expm1(-(preferredMaster.getNormalisedBodyText().length() / 200.0f))));
	}

}
