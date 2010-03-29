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
		NormalisedArticle na=fetch(id);
		if (na==null) {
			na=contentApiNormalisedArticleProvider.normalisedArticleFor(id);
			store(na);
		} else {
			log.info("CACHE-HIT "+id);
		}
		return na;
	}

	private NormalisedArticle fetch(String id) {
		return (NormalisedArticle) cache.get(id);
	}

	public void store(NormalisedArticle na) {
		cache.put(na.getId(), na);
	}

}
