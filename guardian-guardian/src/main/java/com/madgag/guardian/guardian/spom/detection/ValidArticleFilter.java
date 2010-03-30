package com.madgag.guardian.guardian.spom.detection;

import com.google.common.base.Predicate;
import com.google.inject.Inject;
import com.madgag.text.util.LevenshteinWithDistanceThreshold;

public class ValidArticleFilter implements Predicate<NormalisedArticle> {

	private final LevenshteinWithDistanceThreshold levenshteinWithDistanceThreshold;
	
	@Inject
	public ValidArticleFilter(LevenshteinWithDistanceThreshold levenshteinWithDistanceThreshold) {
		this.levenshteinWithDistanceThreshold = levenshteinWithDistanceThreshold;
	}
	
	@Override
	public boolean apply(NormalisedArticle article) {
		int dist = levenshteinWithDistanceThreshold.get("this article has been removed as our copyright has expired", article.getNormalisedBodyText(),6);
		return dist>5;
	}

}
