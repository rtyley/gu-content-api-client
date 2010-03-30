package com.madgag.guardian.guardian.spom.detection;

import java.net.URI;
import java.util.regex.Pattern;

import org.joda.time.DateTime;

import com.google.common.collect.Multimap;
import com.madgag.guardian.contentapi.jaxb.Tag;

public class NormalisedArticle {
	private static final Pattern spacePattern = Pattern.compile("\\s{2,}");
	private static final Pattern nonAlphaNumPattern = Pattern.compile("[^\\s\\p{Alnum}]*");
	private static final Pattern tagPattern = Pattern.compile("<.+?>");

	private String normalisedBodyText;
	private final String id;
	private final Multimap<String, Tag> tags;
	private final DateTime webPublicationDate;
	private final String title;
	private final URI shortUrl;
	
	public String getNormalisedBodyText() {
		return normalisedBodyText;
	}

	public NormalisedArticle(String id, String title, URI shortUrl, String bodyText, DateTime webPublicationDate, Multimap<String, Tag> tags) {
		this.id = id;
		this.title = title;
		this.shortUrl = shortUrl;
		this.webPublicationDate = webPublicationDate;
		this.tags = tags;
		this.normalisedBodyText = normalisedBodyText(bodyText);
		
	}

	private String normalisedBodyText(String bodyText) {
		String textWithNoTags = tagPattern.matcher(bodyText).replaceAll(""); //allTagCleaner.clean(bodyText);
		String textWithoutNonAlphaNum = nonAlphaNumPattern.matcher(textWithNoTags).replaceAll("");
		return spacePattern.matcher(textWithoutNonAlphaNum).replaceAll(" ").trim().toLowerCase();
	}

	public Multimap<String, Tag> getTags() {
		return tags;
	}

	public String getId() {
		return id;
	}

	public DateTime getWebPublicationDate() {
		return webPublicationDate;
	}
	
	public URI getShortUrl() {
		return shortUrl;
	}
	
	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()+"["+getId()+"]";
	}
}
