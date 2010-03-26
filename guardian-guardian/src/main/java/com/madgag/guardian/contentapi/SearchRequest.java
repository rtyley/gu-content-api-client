package com.madgag.guardian.contentapi;

import static org.joda.time.DateTimeZone.UTC;

import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.ReadableInterval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

public class SearchRequest implements ApiRequest {

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd").withZone(UTC);
	private static final Joiner COMMA_JOINER = Joiner.on(",");
	
	Map<String,String> moo=Maps.newHashMap();
	
	public void setTags(String... tags) {
		moo.put("tag", COMMA_JOINER.join(tags));
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
		moo.put("to-date", DATE_FORMAT.print(end));
	}

	public void setFromDate(DateTime start) {
		moo.put("from-date", DATE_FORMAT.print(start));
	}

	public void showFields(String... fields) {
		moo.put("show-fields", COMMA_JOINER.join(fields));
	}

}
