package com.madgag.guardian.guardian;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Sets.newHashSet;

import java.io.Serializable;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;


public class ArticleChronology implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final NavigableMap<DateTime,Set<String>> articleMap=new TreeMap<DateTime, Set<String>>();
	
	private void put(DateTime webPubDate,String contentId ) {
		Set<String> contentIdsForDate = articleMap.get(webPubDate);
		if (contentIdsForDate==null) {
			articleMap.put(webPubDate, contentIdsForDate = newHashSet());
		}
		contentIdsForDate.add(contentId);
	}
	
	public Set<String> contentIdsFor(Interval interval) {
		return newHashSet(concat(articleMap.subMap(interval.getStart(), true, interval.getEnd(), true).values()));
	}

	public void recordPublicationDateOf(NormalisedArticle na) {
		put(na.getWebPublicationDate(),na.getId());
	}
}
