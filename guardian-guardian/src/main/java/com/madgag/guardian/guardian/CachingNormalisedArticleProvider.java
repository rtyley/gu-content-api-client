package com.madgag.guardian.guardian;

import java.util.logging.Logger;

import net.sf.jsr107cache.Cache;

import com.google.inject.Inject;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

public class CachingNormalisedArticleProvider implements NormalisedArticleProvider {

	private static final Logger log = Logger.getLogger(CachingNormalisedArticleProvider.class.getName());
	
	private final ContentApiNormalisedArticleProvider contentApiNormalisedArticleProvider;
	private final Cache cache;

	@Inject
	public CachingNormalisedArticleProvider(Cache cache, ContentApiNormalisedArticleProvider contentApiNormalisedArticleProvider) {
		this.cache = cache;
		this.contentApiNormalisedArticleProvider = contentApiNormalisedArticleProvider;
	}
	
	@Override
	public NormalisedArticle normalisedArticleFor(String id) {
		NormalisedArticle na=(NormalisedArticle) cache.get(id);
		if (na==null) {
			na=contentApiNormalisedArticleProvider.normalisedArticleFor(id);
			cache.put(id, na);
		} else {
			log.info("CACHE-HIT "+id);
		}
		return na;
	}

}
