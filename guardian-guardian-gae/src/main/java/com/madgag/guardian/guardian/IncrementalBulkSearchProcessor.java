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

public class IncrementalBulkSearchProcessor {

	private final PopulatedArticleSearchRequestProvider articleSearchRequestProvider;
	private final CachingNormalisedArticleProvider cachingNormalisedArticleProvider;

	@Inject
	public IncrementalBulkSearchProcessor(
			PopulatedArticleSearchRequestProvider articleSearchRequestProvider,
			CachingNormalisedArticleProvider cachingNormalisedArticleProvider) {
		this.articleSearchRequestProvider = articleSearchRequestProvider;
		this.cachingNormalisedArticleProvider = cachingNormalisedArticleProvider;
	}

	void process(Content content, Interval targetArticleInterval, ArticleChronology articleChronology) {
		articleChronology.recordPublicationDateOf(content);
		if (targetArticleInterval.contains(content.webPublicationDate)) {
			deferSpomSearchFor(content, articleChronology);
		}
	}

	private void deferSpomSearchFor(Content na,	ArticleChronology articleChronology) {
		DateTime webPubDate = na.webPublicationDate;
		Interval interval = new Interval(webPubDate.minus(days(2)), webPubDate);
		Set<String> spomCandidates = articleChronology.contentIdsFor(interval);
		for (List<String> chunkIds : partition(newArrayList(spomCandidates), 50)) {						
			defer(new ArticleSpomSearch(na.getId(), chunkIds),"deferredArticleSpomSearch");
		}
	}

	public SearchRequest createSearchRequestFor(Interval targetArticleInterval) {
		Period bufferPeriod = days(2);

		return articleSearchRequestProvider.nakedArticleSearch().from(
				targetArticleInterval.getStart().minus(bufferPeriod)).to(
				targetArticleInterval.getEnd()).pageSize(50);
	}

}
