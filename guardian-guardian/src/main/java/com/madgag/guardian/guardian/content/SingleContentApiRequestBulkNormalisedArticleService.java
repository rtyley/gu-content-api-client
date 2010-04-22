package com.madgag.guardian.guardian.content;

import static com.google.common.collect.Lists.transform;

import java.util.List;

import com.madgag.guardian.contentapi.SearchRequest;
import com.madgag.guardian.guardian.BulkNormalisedArticleService;
import com.madgag.guardian.guardian.ContentNormaliserTransform;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

final class SingleContentApiRequestBulkNormalisedArticleService implements BulkNormalisedArticleService {
	
	private final SearchRequest req;

	SingleContentApiRequestBulkNormalisedArticleService(SearchRequest req) {
		this.req = req;
	}

	public List<NormalisedArticle> apply(List<String> chunkIds) {
		if (chunkIds.size()>50) {
			throw new IllegalArgumentException();
		}
		return transform(req.withIds(chunkIds).execute().contents, new ContentNormaliserTransform());
	}
}