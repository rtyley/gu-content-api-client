package com.madgag.guardian.guardian;

import com.google.inject.Inject;
import com.madgag.guardian.contentapi.jaxb.PageResponse;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

public class ContentApiNormalisedArticleProvider implements NormalisedArticleProvider {

	private final PopulatedArticleSearchRequestProvider searchRequestProvider;
	private final ContentNormaliserTransform contentNormaliserTransform;
	
	@Inject
	public ContentApiNormalisedArticleProvider(PopulatedArticleSearchRequestProvider searchRequestProvider, ContentNormaliserTransform contentNormaliserTransform) {
		this.searchRequestProvider = searchRequestProvider;
		this.contentNormaliserTransform = contentNormaliserTransform;
	}
	
	@Override
	public NormalisedArticle normalisedArticleFor(String id) {
		PageResponse pageResponse=searchRequestProvider.contentWithId(id).execute();
		return contentNormaliserTransform.apply(pageResponse.content);
	}

}
