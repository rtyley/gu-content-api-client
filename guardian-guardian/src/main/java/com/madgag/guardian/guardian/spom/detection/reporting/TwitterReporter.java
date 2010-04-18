package com.madgag.guardian.guardian.spom.detection.reporting;

import java.net.URI;
import java.util.logging.Logger;

import org.apache.commons.lang.WordUtils;

import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.google.inject.Inject;
import com.madgag.guardian.guardian.DiffUrlGenerator;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;
import com.madgag.guardian.guardian.spom.detection.SpomMatch;
import com.madgag.guardian.guardian.spom.detection.SpomReport;
import com.rosaloves.net.shorturl.bitly.Bitly;

public class TwitterReporter implements SpomDetectionReporter {

	private static final Logger log = Logger.getLogger(SpomDetectionReporter.class.getName());

	private final Twitter twitter;
	private final DiffUrlGenerator diffUrlGenerator = new DiffUrlGenerator();
	private final Bitly bitly;
	
	@Inject
	public TwitterReporter(Twitter twitter, Bitly bitly) {
		this.twitter = twitter;
		this.bitly = bitly;
	}
	
	@Override
	public void report(SpomReport spomReport) {
		for (SpomMatch spomMatch: spomReport.getSpomsWithMatchScores().values()) {
			URI diffUri=diffUrlGenerator.uriFor(spomReport.getTargetArticle().getId(), spomMatch.getSpom().getId());
			String shortDiff=null;
			try {
				shortDiff = bitly.shorten(diffUri.toString()).getShortUrl().toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String tweetText = "Wu-oh: "+shortDiff+" "+spomMatch.getMatchScore().getNormalisedLevenshteinDistance() + " " + quickSummary(spomReport.getTargetArticle()) + " & " + quickSummary(spomMatch.getSpom());
            log.info("tweetText ("+tweetText.length()+" chars): "+tweetText);
			try {
	            twitter.updateStatus(tweetText);
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}
		
	}

	
	private String quickSummary(NormalisedArticle na) {
		return na.getShortUrl()+" \""+ WordUtils.abbreviate(na.getTitle(),23,27,"\u2026")+"\"";
	}

}
