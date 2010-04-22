/**
 * 
 */
package com.madgag.guardian.guardian;

import java.net.URI;

import com.google.common.base.Function;
import com.madgag.guardian.contentapi.jaxb.Content;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

public final class ContentNormaliserTransform implements Function<Content, NormalisedArticle> {
	public NormalisedArticle apply(Content c) {
		String body = c.hasField("body")?c.getField("body"):"";
		URI shortUrl = URI.create(c.getField("short-url"));
		return new NormalisedArticle(c.id, c.webTitle, shortUrl, body, c.webPublicationDate, c.getTagsCategorisedByType() );
	}
}