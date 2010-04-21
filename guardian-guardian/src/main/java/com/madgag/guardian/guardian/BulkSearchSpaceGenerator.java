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
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;
import com.madgag.guardian.guardian.spom.detection.ValidArticleFilter;

public class BulkSearchSpaceGenerator {
	private static final Logger log = Logger.getLogger(BulkSearchSpaceGenerator.class.getName());

	private final SearchRequest articleSearch;
	private final CachingNormalisedArticleProvider cachingNormalisedArticleProvider;
	private final ValidArticleFilter validArticleFilter;

	@Inject
	public BulkSearchSpaceGenerator(PopulatedArticleSearchRequestProvider articleSearchRequestProvider,
			CachingNormalisedArticleProvider cachingNormalisedArticleProvider,
			ValidArticleFilter validArticleFilter) {
		articleSearch=articleSearchRequestProvider.articleSearch();
		this.cachingNormalisedArticleProvider = cachingNormalisedArticleProvider;
		this.validArticleFilter = validArticleFilter;
	}
	
	public SearchSpace getSearchSpaceCovering(Interval interval) {
		log.info("Searching "+interval);
		Period bufferPeriod = days(2);
		SearchResponse boo = articleSearch
						.from(interval.getStart().minus(bufferPeriod))
						.to(interval.getEnd())
						.pageSize(50)
						.execute();
		List<NormalisedArticle> articlesToCheck = newArrayList();
		ArticleChronology articleChronology = new ArticleChronology();
		while (boo != null) {
			for (Content content : boo.contents) {
				NormalisedArticle na = new ContentNormaliserTransform().apply(content);
				if (na != null) {
					cachingNormalisedArticleProvider.store(na);
					if (validArticleFilter.apply(na)) {
						articleChronology.recordPublicationDateOf(na);
						if (interval.contains(content.webPublicationDate)) {
							articlesToCheck.add(na);
						}
					}
				}
			}
			boo = boo.next();
		}
		return new SearchSpace(articlesToCheck, articleChronology, bufferPeriod);
	}

}
