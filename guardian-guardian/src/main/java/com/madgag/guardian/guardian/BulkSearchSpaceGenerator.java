package com.madgag.guardian.guardian;

import static com.google.common.collect.Lists.newArrayList;
import static org.joda.time.Period.days;

import java.util.List;
import java.util.logging.Logger;

import org.joda.time.Interval;
import org.joda.time.Period;

import com.google.inject.Inject;
import com.madgag.guardian.contentapi.SearchRequest;
import com.madgag.guardian.contentapi.jaxb.Content;
import com.madgag.guardian.contentapi.jaxb.SearchResponse;

public class BulkSearchSpaceGenerator {
	private static final Logger log = Logger
			.getLogger(BulkSearchSpaceGenerator.class.getName());

	private final SearchRequest articleSearch;

	@Inject
	public BulkSearchSpaceGenerator(PopulatedArticleSearchRequestProvider articleSearchRequestProvider) {
		articleSearch = articleSearchRequestProvider.nakedArticleSearch();
	}

	public SearchSpace getSearchSpaceCovering(Interval interval) {
		log.info("Searching " + interval);
		Period bufferPeriod = days(2);
		SearchResponse boo = articleSearch.from(
				interval.getStart().minus(bufferPeriod)).to(interval.getEnd())
				.pageSize(50).execute();
		List<String> articlesToCheck = newArrayList();
		ArticleChronology articleChronology = new ArticleChronology();
		while (boo != null) {
			for (Content content : boo.contents) {
				articleChronology.recordPublicationDateOf(content);
				if (interval.contains(content.webPublicationDate)) {
					articlesToCheck.add(content.getId());
				}
			}
			boo = boo.next();
		}
		return new SearchSpace(articlesToCheck, articleChronology, bufferPeriod);
	}

}
