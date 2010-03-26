package com.madgag.guardian.contentapi;

import static org.joda.time.DateTimeZone.UTC;

import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadableInterval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

public class SearchRequest implements ApiRequest {

	DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd").withZone(UTC);
	
	Map<String,String> moo=Maps.newHashMap();
	
	public void setTags(String... tags) {
		moo.put("tag", Joiner.on(",").join(tags));
	}
	
	@Override
	public Map<String, String> getParams() {
		return moo;
	}

	@Override
	public String getPathPrefix() {
		return "search";
	}

	public void setInterval(ReadableInterval interval) {
		setFromDate(interval.getStart());
		setToDate(interval.getEnd());
	}

	public void setToDate(DateTime end) {
		moo.put("to-date", dateFormat.print(end));
	}

	public void setFromDate(DateTime start) {
		moo.put("from-date", dateFormat.print(start));
	}

}
