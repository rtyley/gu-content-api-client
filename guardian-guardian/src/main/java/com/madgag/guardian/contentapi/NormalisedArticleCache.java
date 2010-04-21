package com.madgag.guardian.contentapi;

import static com.google.common.base.Predicates.notNull;
import static com.google.common.collect.Maps.filterValues;
import static com.google.common.collect.Maps.uniqueIndex;
import static com.madgag.guardian.contentapi.jaxb.HasId.GET_ID;
import static java.util.Collections.emptyMap;
import static java.util.logging.Level.FINE;

import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;

import com.google.inject.Inject;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

public class NormalisedArticleCache {
	
	private static final Logger log = Logger.getLogger(NormalisedArticleCache.class.getName());
	
	private final Cache cache;
	
	@Inject
	public NormalisedArticleCache(Cache cache) {
		this.cache = cache;
	}

	public void store(Iterable<NormalisedArticle> normalisedArticles) {
		cache.putAll(uniqueIndex(normalisedArticles, GET_ID));
	}

	@SuppressWarnings("unchecked")
	public Map<String, NormalisedArticle> getAll(Collection<String> ids) {
		try {
			Map<String,NormalisedArticle> articlesFoundInCache = filterValues(cache.getAll(ids), notNull());
			if (log.isLoggable(FINE))
				log.fine("NA Cache MULIT-HIT : "+articlesFoundInCache.size()+" / "+ids.size());
			return articlesFoundInCache;
		} catch (CacheException e) {
			e.printStackTrace();
			return emptyMap();
		}
	}
}
