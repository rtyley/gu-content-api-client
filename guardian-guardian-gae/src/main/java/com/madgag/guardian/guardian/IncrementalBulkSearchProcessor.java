package com.madgag.guardian.guardian;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.partition;
import static com.newatlanta.appengine.taskqueue.Deferred.defer;
import static org.joda.time.Period.days;

import java.util.Collection;
import java.util.List;
import java.util.SortedMap;

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

	void process(Content content, Interval targetArticleInterval, SortedMap<DateTime, String> searchSpaceArticleIds) {
		NormalisedArticle na = new ContentNormaliserTransform().apply(content);
		if (na != null) {
			cachingNormalisedArticleProvider.store(na);
			if (validArticleFilter.apply(na)) {
				DateTime webPubDate = na.getWebPublicationDate();
				searchSpaceArticleIds.put(webPubDate, na.getId());
				if (targetArticleInterval.contains(webPubDate)) {
					Collection<String> possibleSpomIds = searchSpaceArticleIds.subMap(webPubDate.minus(days(2)), webPubDate).values();
					for (List<String> chunkIds : partition(newArrayList(possibleSpomIds), 60)) {						
						defer(new ArticleSpomSearch(na.getId(), chunkIds),"deferredArticleSpomSearch");
					}
				}
			}
		}
	}

	public SearchRequest createSearchRequestFor(Interval targetArticleInterval) {
		Period bufferPeriod = days(2);

		return articleSearchRequestProvider.articleSearch().from(
				targetArticleInterval.getStart().minus(bufferPeriod)).to(
				targetArticleInterval.getEnd()).pageSize(50);
	}

}
