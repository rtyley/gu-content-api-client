/**
 * 
 */
package com.madgag.guardian.guardian;

import static com.google.common.collect.Sets.newHashSet;

import com.google.common.base.Function;
import com.madgag.guardian.contentapi.jaxb.Content;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

public final class ContentNormaliserTransform implements Function<Content, NormalisedArticle> {
	public NormalisedArticle apply(Content c) {
		String body = c.getField("body");
		return new NormalisedArticle(c.id, body, c.webPublicationDate, newHashSet("foo") );
	}
}