/**
 * 
 */
package com.madgag.guardian.guardian;

import java.net.URI;
import java.net.URISyntaxException;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.madgag.guardian.contentapi.jaxb.Content;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

public final class ContentNormaliserTransform implements Function<Content, NormalisedArticle> {
	public NormalisedArticle apply(Content c) {
		if (!c.hasField("body")) {
			return null;
		}
		String body = c.getField("body");
		URI shortUrl;
		try {
			shortUrl= new URI(c.getField("short-url"));
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		return new NormalisedArticle(c.id, c.webTitle, shortUrl, body, c.webPublicationDate, c.getTagsCategorisedByType() );
	}
}