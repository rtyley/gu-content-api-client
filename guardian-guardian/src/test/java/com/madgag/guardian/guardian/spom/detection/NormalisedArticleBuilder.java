package com.madgag.guardian.guardian.spom.detection;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.madgag.guardian.contentapi.jaxb.Tag;

public class NormalisedArticleBuilder {

	private String originalBody;

	public NormalisedArticleBuilder originalBody(String originalBody) {
		this.originalBody = originalBody;
		return this;
	}

	public NormalisedArticle toArticle() {
		Multimap<String, Tag> tags=ImmutableMultimap.<String,Tag>of();
		return new NormalisedArticle("", "", null, originalBody, new DateTime(), tags );
	}

}
