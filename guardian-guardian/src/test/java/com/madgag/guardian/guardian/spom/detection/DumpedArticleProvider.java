package com.madgag.guardian.guardian.spom.detection;

import java.io.InputStream;

import com.google.common.base.Function;
import com.madgag.guardian.contentapi.ContentApiException;
import com.madgag.guardian.contentapi.jaxb.PageResponse;
import com.madgag.guardian.guardian.ContentNormaliserTransform;

public class DumpedArticleProvider implements Function<String,NormalisedArticle> {

	@Override
	public NormalisedArticle apply(String id) {
		try {
			String path="/sample-articles/"+id.replace('/', '.')+".xml";
			InputStream resourceStream = DumpedArticleProvider.class.getResourceAsStream(path);
			if (resourceStream==null) {
				throw new RuntimeException("Couldn't find : "+path);
			}
			PageResponse pageResponse = (PageResponse) PageResponse.JAXB_CONTEXT.createUnmarshaller().unmarshal(resourceStream);
			return new ContentNormaliserTransform().apply(pageResponse.content);
		} catch (Exception e) {
			throw new ContentApiException(e);
		}
	}

}
