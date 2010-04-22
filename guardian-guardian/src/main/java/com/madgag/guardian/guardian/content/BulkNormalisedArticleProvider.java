package com.madgag.guardian.guardian.content;

import java.util.Set;

import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

public interface BulkNormalisedArticleProvider {

	public Iterable<NormalisedArticle> normalisedArticlesFor(Set<String> ids);
}
