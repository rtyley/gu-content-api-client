package com.madgag.guardian.contentapi;

import static com.google.common.collect.Maps.newHashMap;
import static org.joda.time.DateTimeZone.UTC;

import java.util.Map;

import javax.xml.bind.JAXBContext;

import org.joda.time.DateTime;
import org.joda.time.ReadableInterval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.base.Joiner;
import com.madgag.guardian.contentapi.jaxb.SearchResponse;

public class SearchRequest implements ApiRequest<SearchResponse> {

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd").withZone(UTC);
	private static final Joiner COMMA_JOINER = Joiner.on(",");
	
	Map<String,String> moo=newHashMap();
	
	private final Hitter hitter;
	
	public SearchRequest(Hitter hitter) {
		this.hitter = hitter;
	}

	public SearchRequest withTags(String... tags) {
		moo.put("tag", COMMA_JOINER.join(tags));
		return this;
	}
	
	@Override
	public Map<String, String> getParams() {
		return moo;
	}

	@Override
	public String getPathPrefix() {
		return "search";
	}


	public SearchRequest to(DateTime end) {
		moo.put("to-date", DATE_FORMAT.print(end));
		return this;
	}

	public SearchRequest from(DateTime start) {
		moo.put("from-date", DATE_FORMAT.print(start));
		return this;
	}

	public SearchRequest showFields(String... fields) {
		moo.put("show-fields", COMMA_JOINER.join(fields));
		return this;
	}

	public SearchRequest during(ReadableInterval searchInterval) {
		return from(searchInterval.getStart()).to(searchInterval.getEnd());
	}

	public SearchResponse execute() {
		return hitter.jojo(this);
	}
	
	public JAXBContext getJaxbContextForResponse() {
		return SearchResponse.JAXB_CONTEXT;
	}

	public SearchRequest pageSize(int itemsPerPage) {
		moo.put("page-size", ""+itemsPerPage);
		return this;
	}

}
