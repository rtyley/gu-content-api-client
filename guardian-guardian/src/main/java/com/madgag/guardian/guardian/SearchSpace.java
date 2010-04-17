package com.madgag.guardian.guardian;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

public class SearchSpace {

	private final List<NormalisedArticle> articlesToCheck;
	private final Period bufferPeriod;
	private final ArticleChronology articleChronology;

	public SearchSpace(List<NormalisedArticle> articlesToCheck,	ArticleChronology articleChronology,Period bufferPeriod) {
		this.articlesToCheck = articlesToCheck;
		this.articleChronology = articleChronology;
		this.bufferPeriod = bufferPeriod;
	}

	public List<NormalisedArticle> getArticlesToCheck() {
		return articlesToCheck;
	}
	
	Set<String> possibleSpomIdsFor(NormalisedArticle preferredMaster) {
		DateTime articleDate = preferredMaster.getWebPublicationDate();
		return articleChronology.contentIdsFor(new Interval(articleDate.minus(bufferPeriod), articleDate));
	}
}
