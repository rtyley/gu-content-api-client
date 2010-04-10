package com.madgag.guardian.guardian;

import java.util.Collection;
import java.util.List;
import java.util.SortedMap;

import org.joda.time.DateTime;
import org.joda.time.Period;

import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

public class SearchSpace {

	private final List<NormalisedArticle> articlesToCheck;
	private final SortedMap<DateTime, String> possibleSpomIds;
	private final Period bufferPeriod;

	public SearchSpace(List<NormalisedArticle> articlesToCheck,	SortedMap<DateTime, String> possibleSpomIds,Period bufferPeriod) {
		this.articlesToCheck = articlesToCheck;
		this.possibleSpomIds = possibleSpomIds;
		this.bufferPeriod = bufferPeriod;
	}

	public List<NormalisedArticle> getArticlesToCheck() {
		return articlesToCheck;
	}
	
	Collection<String> possibleSpomIdsFor(NormalisedArticle preferredMaster) {
		DateTime articleDate = preferredMaster.getWebPublicationDate();
		return possibleSpomIds.subMap(articleDate.minus(bufferPeriod), articleDate).values();
	}
}
