package com.madgag.guardian.contentapi;

import static com.google.common.collect.Lists.transform;

import java.util.List;

import com.madgag.guardian.guardian.ContentNormaliserTransform;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

final class ContentApiBulkNormalisedArticleService implements BulkNormalisedArticleService {
	
	private final SearchRequest req;

	ContentApiBulkNormalisedArticleService(SearchRequest req) {
		this.req = req;
	}

	public List<NormalisedArticle> apply(List<String> chunkIds) {
		if (chunkIds.size()>50) {
			throw new IllegalArgumentException();
		}
		return transform(req.withIds(chunkIds).execute().contents, new ContentNormaliserTransform());
	}
}