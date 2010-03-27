package com.madgag.guardian.guardian;

import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

public interface NormalisedArticleProvider {

	public NormalisedArticle normalisedArticleFor(String id);
}
