package com.madgag.guardian.guardian.spom.detection;

import static com.google.common.collect.Sets.newHashSet;
import static java.lang.Math.round;

import java.util.Collection;
import java.util.logging.Logger;

import org.apache.commons.lang.WordUtils;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.google.inject.Inject;
import com.madgag.guardian.guardian.NormalisedArticleProvider;

public class SpomIdentifier {

	private static final Logger log = Logger.getLogger(SpomIdentifier.class.getName());
	
	private final SpomMatchScorer spomMatchScorer;

	private final NormalisedArticleProvider articleProvider;

	private final Twitter twitter;

	@Inject
	public SpomIdentifier(SpomMatchScorer spomMatchScorer, NormalisedArticleProvider articleProvider, Twitter twitter) {
		this.spomMatchScorer = spomMatchScorer;
		this.articleProvider = articleProvider;
		this.twitter = twitter;
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
					reportStuff(preferredMaster, possibleSpom, currentMatchScore);
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

	private void reportStuff(NormalisedArticle preferredMaster, NormalisedArticle possibleSpom, float currentMatchScore) {
		try {
            String tweetText = "Wu-oh : \u0394=" + round(currentMatchScore * 100) + "% " + quickSummary(preferredMaster) + " & " + quickSummary(possibleSpom);
            log.info("tweetText ("+tweetText.length()+" chars): "+tweetText);
            twitter.updateStatus(tweetText);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	private String quickSummary(NormalisedArticle na) {
		return na.getShortUrl()+" \""+ WordUtils.abbreviate(na.getTitle(),35,40,"\u2026")+"\"";
	}
}
