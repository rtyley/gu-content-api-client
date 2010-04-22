package com.madgag.guardian.guardian;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Maps.newTreeMap;
import static com.google.common.collect.Sets.newHashSet;

import java.io.Serializable;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.madgag.guardian.contentapi.jaxb.Content;


public class ArticleChronology implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final Map<String,DateTime> pubMap=newHashMap();
	private final NavigableMap<DateTime,Set<String>> articleMap=newTreeMap();
	
	private void put(DateTime webPubDate,String contentId ) {
		pubMap.put(contentId, webPubDate);
		Set<String> contentIdsForDate = articleMap.get(webPubDate);
		if (contentIdsForDate==null) {
			articleMap.put(webPubDate, contentIdsForDate = newHashSet());
		}
		contentIdsForDate.add(contentId);
	}
	
	
	public DateTime publicationDateOf(String contentId) {
		return pubMap.get(contentId);
	}
	
	public Set<String> contentIdsFor(Interval interval) {
		return newHashSet(concat(articleMap.subMap(interval.getStart(), true, interval.getEnd(), true).values()));
	}

	public void recordPublicationDateOf(Content article) {
		put(article.webPublicationDate,article.getId());
	}
}
