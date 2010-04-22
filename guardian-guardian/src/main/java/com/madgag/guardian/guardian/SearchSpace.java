package com.madgag.guardian.guardian;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

public class SearchSpace {

	private final List<String> articlesToCheck;
	private final Period bufferPeriod;
	private final ArticleChronology articleChronology;

	public SearchSpace(List<String> articlesToCheck,	ArticleChronology articleChronology,Period bufferPeriod) {
		this.articlesToCheck = articlesToCheck;
		this.articleChronology = articleChronology;
		this.bufferPeriod = bufferPeriod;
	}

	public List<String> getArticlesToCheck() {
		return articlesToCheck;
	}
	
	Set<String> possibleSpomIdsFor(String preferredMasterId) {
		DateTime articleDate = articleChronology.publicationDateOf(preferredMasterId);
		return articleChronology.contentIdsFor(new Interval(articleDate.minus(bufferPeriod), articleDate));
	}
}
