package com.madgag.guardian.guardian.spom.detection;

import static java.lang.Math.round;

import java.util.logging.Logger;

import org.apache.commons.lang.WordUtils;

import com.google.inject.Inject;

import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TwitterReporter implements SpomDetectionReporter {

	private static final Logger log = Logger.getLogger(SpomDetectionReporter.class.getName());

	private final Twitter twitter;
	
	@Inject
	public TwitterReporter(Twitter twitter) {
		this.twitter = twitter;
	}
	
	@Override
	public void reportStuff(NormalisedArticle preferredMaster, NormalisedArticle possibleSpom, float currentMatchScore) {
		try {
            String tweetText = "Wu-oh : \u0394=" + round(currentMatchScore * 100) + "% " + quickSummary(preferredMaster) + " & " + quickSummary(possibleSpom);
            log.info("tweetText ("+tweetText.length()+" chars): "+tweetText);
            twitter.updateStatus(tweetText);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
	

	private String quickSummary(NormalisedArticle na) {
		return na.getShortUrl()+" \""+ WordUtils.abbreviate(na.getTitle(),29,35,"\u2026")+"\"";
	}

}
