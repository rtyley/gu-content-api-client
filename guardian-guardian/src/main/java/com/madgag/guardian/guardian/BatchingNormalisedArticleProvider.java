package com.madgag.guardian.guardian;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.difference;
import static com.madgag.guardian.contentapi.BulkContentByIdService.batchProcess;

import java.util.Map;
import java.util.Set;

import com.google.inject.Inject;
import com.madgag.guardian.contentapi.CachingBulkNormalisedArticleService;
import com.madgag.guardian.contentapi.NormalisedArticleCache;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

public class BatchingNormalisedArticleProvider implements BulkNormalisedArticleProvider {
	private final NormalisedArticleCache cache;
	private final CachingBulkNormalisedArticleService cachingBulkNormalisedArticleService;
	
	@Inject
	public BatchingNormalisedArticleProvider(NormalisedArticleCache cache,
			CachingBulkNormalisedArticleService cachingBulkNormalisedArticleService) {
		this.cache = cache;
		this.cachingBulkNormalisedArticleService = cachingBulkNormalisedArticleService;
	}
	
	@Override
	public Iterable<NormalisedArticle> normalisedArticleFor(Set<String> ids) {
		Map<String, NormalisedArticle> foundFromCache = cache.getAll(ids);
		Set<String> idsNotInCache = difference(ids, foundFromCache.keySet());
		Iterable<NormalisedArticle> normalisedArticlesDirectFromApi = 
			batchProcess(newArrayList(idsNotInCache), 20, cachingBulkNormalisedArticleService);
		
		return concat(normalisedArticlesDirectFromApi,foundFromCache.values());
	}

	
	

}
