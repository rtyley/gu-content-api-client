package com.madgag.guardian.guardian;

import com.google.inject.Inject;
import com.madgag.guardian.contentapi.ContentApiClient;
import com.madgag.guardian.contentapi.jaxb.PageResponse;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

public class ContentApiNormalisedArticleProvider implements NormalisedArticleProvider {

	private final ContentApiClient apiClient;
	private final ContentNormaliserTransform contentNormaliserTransform;
	
	@Inject
	public ContentApiNormalisedArticleProvider(ContentApiClient apiClient, ContentNormaliserTransform contentNormaliserTransform) {
		this.apiClient = apiClient;
		this.contentNormaliserTransform = contentNormaliserTransform;
	}
	
	@Override
	public NormalisedArticle normalisedArticleFor(String id) {
		PageResponse pageResponse=apiClient.loadPageWith(id).showFields("body").execute();
		return contentNormaliserTransform.apply(pageResponse.content);
	}

}
