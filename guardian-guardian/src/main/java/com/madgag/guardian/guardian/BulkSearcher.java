package com.madgag.guardian.guardian;

import static com.google.inject.internal.Maps.newTreeMap;
import static org.joda.time.Period.days;

import java.util.Collection;
import java.util.List;
import java.util.SortedMap;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.ReadableInstant;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.madgag.guardian.contentapi.ContentApiClient;
import com.madgag.guardian.contentapi.jaxb.Content;
import com.madgag.guardian.contentapi.jaxb.SearchResponse;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;
import com.madgag.guardian.guardian.spom.detection.SpomIdentifier;

public class BulkSearcher {
	private final ContentApiClient apiClient;
	private final CachingNormalisedArticleProvider cachingNormalisedArticleProvider;
	private final SpomIdentifier spomIdentifier;

	@Inject
	public BulkSearcher(ContentApiClient apiClient,CachingNormalisedArticleProvider cachingNormalisedArticleProvider,SpomIdentifier spomIdentifier) {
		this.apiClient = apiClient;
		this.cachingNormalisedArticleProvider = cachingNormalisedArticleProvider;
		this.spomIdentifier = spomIdentifier;
	}
	
	public void search(Interval interval) {
		Period bufferPeriod = days(2);
		SearchResponse boo = apiClient.search()
			.withTags("type/article")
			.showFields("body","short-url")
			.showTags("all")
			.from(interval.getStart().minus(bufferPeriod)).to(interval.getEnd())
			.pageSize(50)
			.execute();
		List<NormalisedArticle> articlesToCheck= Lists.newArrayList();
		SortedMap<DateTime,String> possibleSpomIds=Maps.newTreeMap();
		while (boo!=null) {
			for (Content content : boo.contents) {
				NormalisedArticle na = new ContentNormaliserTransform().apply(content);
				if (na!=null) {
					cachingNormalisedArticleProvider.store(na);
					possibleSpomIds.put(na.getWebPublicationDate(), na.getId());
					if (interval.contains(content.webPublicationDate)) {					
						articlesToCheck.add(na);
					}
				}
			}
			boo=boo.next();
		}
		for (NormalisedArticle preferredMaster : articlesToCheck) {
			DateTime articleDate = preferredMaster.getWebPublicationDate();
			Collection<String> possibleSpomsForArticleWithinBufferPeriod = possibleSpomIds.subMap(articleDate.minus(bufferPeriod), articleDate).values();
			spomIdentifier.identifySpomsFor(preferredMaster, possibleSpomsForArticleWithinBufferPeriod);
		}
	}
	
}
