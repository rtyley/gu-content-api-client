package com.madgag.guardian.guardian.spom.detection.reporting;

import static java.lang.Math.round;

import java.util.logging.Logger;

import org.apache.commons.lang.WordUtils;

import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.google.inject.Inject;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;
import com.madgag.guardian.guardian.spom.detection.SpomMatch;
import com.madgag.guardian.guardian.spom.detection.SpomReport;

public class TwitterReporter implements SpomDetectionReporter {

	private static final Logger log = Logger.getLogger(SpomDetectionReporter.class.getName());

	private final Twitter twitter;
	
	@Inject
	public TwitterReporter(Twitter twitter) {
		this.twitter = twitter;
	}
	
	@Override
	public void report(SpomReport spomReport) {
		for (SpomMatch detectedSpom: spomReport.getSpomsWithMatchScores().values()) {
            int textDiff = round(detectedSpom.getMatchScore().getNormalisedLevenshteinDistance().getValue() * 100);
			String tweetText = "Wu-oh : \u0394=" + textDiff + "% " + quickSummary(spomReport.getTargetArticle()) + " & " + quickSummary(detectedSpom.getSpom());
            log.info("tweetText ("+tweetText.length()+" chars): "+tweetText);
			try {
	            twitter.updateStatus(tweetText);
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private String quickSummary(NormalisedArticle na) {
		return na.getShortUrl()+" \""+ WordUtils.abbreviate(na.getTitle(),29,35,"\u2026")+"\"";
	}

}
