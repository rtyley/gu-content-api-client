package com.madgag.guardian.guardian;

import java.util.Set;

import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

public interface BulkNormalisedArticleProvider {

	public Iterable<NormalisedArticle> normalisedArticleFor(Set<String> ids);
}
