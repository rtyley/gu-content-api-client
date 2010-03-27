package com.madgag.guardian.guardian;

import static com.google.common.collect.Sets.newHashSet;

import com.google.inject.Inject;
import com.madgag.guardian.contentapi.Hitter;
import com.madgag.guardian.contentapi.PageRequest;
import com.madgag.guardian.contentapi.jaxb.Content;
import com.madgag.guardian.contentapi.jaxb.PageResponse;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

public class ContentApiNormalisedArticleProvider implements NormalisedArticleProvider {

	private final Hitter hitter;
	
	@Inject
	public ContentApiNormalisedArticleProvider(Hitter hitter) {
		this.hitter = hitter;
	}
	
	@Override
	public NormalisedArticle normalisedArticleFor(String id) {
		PageRequest pageRequest = new PageRequest(id);
		PageResponse pageResponse;
		try {
			pageResponse = hitter.jojo(pageRequest);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Content content = pageResponse.content;
		return new NormalisedArticle(content.id,content.getField("body"),newHashSet("foo"));
	}

}
