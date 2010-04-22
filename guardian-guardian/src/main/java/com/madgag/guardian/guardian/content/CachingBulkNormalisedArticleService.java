package com.madgag.guardian.guardian.content;

import java.util.List;

import com.google.inject.Inject;
import com.madgag.guardian.contentapi.NormalisedArticleCache;
import com.madgag.guardian.guardian.BulkNormalisedArticleService;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

public class CachingBulkNormalisedArticleService implements BulkNormalisedArticleService {
	
	private final SingleContentApiRequestBulkNormalisedArticleService bulk;
	private final NormalisedArticleCache articleCache;

	@Inject
	public CachingBulkNormalisedArticleService(SingleContentApiRequestBulkNormalisedArticleService bulk, NormalisedArticleCache articleCache) {
		this.bulk = bulk;
		this.articleCache = articleCache;
	}

	public List<NormalisedArticle> apply(List<String> chunkIds) {
		List<NormalisedArticle> normalisedArticles=bulk.apply(chunkIds);
		articleCache.store(normalisedArticles);
		return normalisedArticles;
	}
}
