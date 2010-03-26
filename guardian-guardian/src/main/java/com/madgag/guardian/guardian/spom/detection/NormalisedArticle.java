package com.madgag.guardian.guardian.spom.detection;

import static java.util.Collections.unmodifiableSet;

import java.util.Collection;
import java.util.Set;
import java.util.regex.Pattern;

public class NormalisedArticle {
	private static final Pattern spacePattern = Pattern.compile("\\s{2,}");
	private static final Pattern nonAlphaNumPattern = Pattern.compile("[^\\s\\p{Alnum}]*");

	private String normalisedBodyText;
	private final String id;
	private final Set<String> contributorIds;
	
	public String getNormalisedBodyText() {
		return normalisedBodyText;
	}

	public NormalisedArticle(String id, String bodyText, Set<String> contributorIds) {
		this.id = id;
		this.contributorIds = unmodifiableSet(contributorIds);
		this.normalisedBodyText = normalisedBodyText(bodyText);
		
	}

	private String normalisedBodyText(String bodyText) {
		//AllTagCleaner allTagCleaner = new AllTagCleaner();
		String textWithNoTags = bodyText; //allTagCleaner.clean(bodyText);
		String textWithoutNonAlphaNum = nonAlphaNumPattern.matcher(textWithNoTags).replaceAll("");
		return spacePattern.matcher(textWithoutNonAlphaNum).replaceAll(" ").trim().toLowerCase();
	}

	public Collection<? extends String> getContributorIds() {
		return contributorIds;
	}

	public String getId() {
		return id;
	}

}
