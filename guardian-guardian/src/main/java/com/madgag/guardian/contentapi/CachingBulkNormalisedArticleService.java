package com.madgag.guardian.contentapi;

import java.util.List;

import com.google.inject.Inject;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

public class CachingBulkNormalisedArticleService implements BulkNormalisedArticleService {
	
	private final ContentApiBulkNormalisedArticleService bulk;
	private final NormalisedArticleCache articleCache;

	@Inject
	public CachingBulkNormalisedArticleService(ContentApiBulkNormalisedArticleService bulk, NormalisedArticleCache articleCache) {
		this.bulk = bulk;
		this.articleCache = articleCache;
	}

	public List<NormalisedArticle> apply(List<String> chunkIds) {
		List<NormalisedArticle> normalisedArticles=bulk.apply(chunkIds);
		articleCache.store(normalisedArticles);
		return normalisedArticles;
	}
}
