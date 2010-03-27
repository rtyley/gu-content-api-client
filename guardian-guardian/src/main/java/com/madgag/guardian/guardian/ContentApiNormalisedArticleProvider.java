package com.madgag.guardian.guardian;

import com.google.inject.Inject;
import com.madgag.guardian.contentapi.Hitter;
import com.madgag.guardian.contentapi.PageRequest;
import com.madgag.guardian.contentapi.jaxb.PageResponse;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

public class ContentApiNormalisedArticleProvider implements NormalisedArticleProvider {

	private final Hitter hitter;
	private final ContentNormaliserTransform contentNormaliserTransform;
	
	@Inject
	public ContentApiNormalisedArticleProvider(Hitter hitter, ContentNormaliserTransform contentNormaliserTransform) {
		this.hitter = hitter;
		this.contentNormaliserTransform = contentNormaliserTransform;
	}
	
	@Override
	public NormalisedArticle normalisedArticleFor(String id) {
		PageRequest pageRequest = new PageRequest(id);
		PageResponse pageResponse;
		try {
			pageResponse = hitter.jojo(pageRequest);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return contentNormaliserTransform.apply(pageResponse.content);
	}

}
