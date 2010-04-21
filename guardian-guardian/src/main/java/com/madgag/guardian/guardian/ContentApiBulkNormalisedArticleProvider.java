package com.madgag.guardian.guardian;

import static com.google.common.collect.Iterables.transform;

import java.util.Set;

import com.madgag.guardian.contentapi.BulkContentByIdService;
import com.madgag.guardian.contentapi.SearchRequest;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

public class ContentApiBulkNormalisedArticleProvider implements BulkNormalisedArticleProvider {
	
	private final BulkContentByIdService bulkContentByIdService;
	private final SearchRequest searchRequest;
	private final ContentNormaliserTransform contentNormaliserTransform;
	
	public ContentApiBulkNormalisedArticleProvider(
			BulkContentByIdService bulkContentByIdService,
			PopulatedArticleSearchRequestProvider searchRequestProvider,
			ContentNormaliserTransform contentNormaliserTransform) {
		this.bulkContentByIdService = bulkContentByIdService;
		this.searchRequest = searchRequestProvider.articleSearch();
		this.contentNormaliserTransform = contentNormaliserTransform;
	}
	
	@Override
	public Iterable<NormalisedArticle> normalisedArticleFor(Set<String> ids) {
		return transform(bulkContentByIdService.contentFor(searchRequest, ids), contentNormaliserTransform);
	}

}
