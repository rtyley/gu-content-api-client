package com.madgag.guardian.guardian.spom.detection;

import static java.util.Collections.unmodifiableSet;

import java.util.Collection;
import java.util.Set;
import java.util.regex.Pattern;

import org.joda.time.DateTime;

public class NormalisedArticle {
	private static final Pattern spacePattern = Pattern.compile("\\s{2,}");
	private static final Pattern nonAlphaNumPattern = Pattern.compile("[^\\s\\p{Alnum}]*");
	private static final Pattern tagPattern = Pattern.compile("<.+?>");

	private String normalisedBodyText;
	private final String id;
	private final Set<String> contributorIds;
	private final DateTime webPublicationDate;
	
	public String getNormalisedBodyText() {
		return normalisedBodyText;
	}

	public NormalisedArticle(String id, String bodyText, DateTime webPublicationDate, Set<String> contributorIds) {
		this.id = id;
		this.webPublicationDate = webPublicationDate;
		this.contributorIds = unmodifiableSet(contributorIds);
		this.normalisedBodyText = normalisedBodyText(bodyText);
		
	}

	private String normalisedBodyText(String bodyText) {
		//AllTagCleaner allTagCleaner = new AllTagCleaner();
		String textWithNoTags = tagPattern.matcher(bodyText).replaceAll(""); //allTagCleaner.clean(bodyText);
		String textWithoutNonAlphaNum = nonAlphaNumPattern.matcher(textWithNoTags).replaceAll("");
		return spacePattern.matcher(textWithoutNonAlphaNum).replaceAll(" ").trim().toLowerCase();
	}

	public Collection<? extends String> getContributorIds() {
		return contributorIds;
	}

	public String getId() {
		return id;
	}

	public DateTime getWebPublicationDate() {
		return webPublicationDate;
	}

}
