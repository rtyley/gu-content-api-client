package com.madgag.guardian.guardian;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.partition;
import static com.newatlanta.appengine.taskqueue.Deferred.defer;
import static org.joda.time.Period.days;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import com.google.inject.Inject;
import com.madgag.guardian.contentapi.SearchRequest;
import com.madgag.guardian.contentapi.jaxb.Content;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;
import com.madgag.guardian.guardian.spom.detection.ValidArticleFilter;

public class IncrementalBulkSearchProcessor {

	private final PopulatedArticleSearchRequestProvider articleSearchRequestProvider;
	private final CachingNormalisedArticleProvider cachingNormalisedArticleProvider;
	private final ValidArticleFilter validArticleFilter;

	@Inject
	public IncrementalBulkSearchProcessor(
			PopulatedArticleSearchRequestProvider articleSearchRequestProvider,
			CachingNormalisedArticleProvider cachingNormalisedArticleProvider,
			ValidArticleFilter validArticleFilter) {
		this.articleSearchRequestProvider = articleSearchRequestProvider;
		this.cachingNormalisedArticleProvider = cachingNormalisedArticleProvider;
		this.validArticleFilter = validArticleFilter;
	}

	void process(Content content, Interval targetArticleInterval, ArticleChronology articleChronology) {
		NormalisedArticle na = new ContentNormaliserTransform().apply(content);
		if (na != null) {
			cachingNormalisedArticleProvider.store(na);
			if (validArticleFilter.apply(na)) {
				articleChronology.recordPublicationDateOf(na);
				if (targetArticleInterval.contains(na.getWebPublicationDate())) {
					deferSpomSearchFor(na, articleChronology);
				}
			}
		}
	}

	private void deferSpomSearchFor(NormalisedArticle na,
			ArticleChronology articleChronology) {
		DateTime webPubDate = na.getWebPublicationDate();
		Interval interval = new Interval(webPubDate.minus(days(2)), webPubDate);
		Set<String> spomCandidates = articleChronology.contentIdsFor(interval);
		for (List<String> chunkIds : partition(newArrayList(spomCandidates), 50)) {						
			defer(new ArticleSpomSearch(na.getId(), chunkIds),"deferredArticleSpomSearch");
		}
	}

	public SearchRequest createSearchRequestFor(Interval targetArticleInterval) {
		Period bufferPeriod = days(2);

		return articleSearchRequestProvider.articleSearch().from(
				targetArticleInterval.getStart().minus(bufferPeriod)).to(
				targetArticleInterval.getEnd()).pageSize(50);
	}

}
