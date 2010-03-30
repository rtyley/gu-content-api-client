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
import com.google.common.collect.ImmutableMap;
import com.madgag.guardian.contentapi.jaxb.SearchResponse;

public class SearchRequest implements ApiRequest<SearchResponse> {

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd").withZone(UTC);
	private static final Joiner COMMA_JOINER = Joiner.on(",");
	
	private final ImmutableMap<String,String> moo;
	
	private final Hitter hitter;
	
	public SearchRequest(Hitter hitter) {
		this.hitter = hitter;
		moo = ImmutableMap.of();
	}
	
	public SearchRequest(Hitter hitter, ImmutableMap<String,String> moo) {
		this.hitter = hitter;
		this.moo = moo;
	}

	public SearchRequest withTags(String... tags) {
		return newSearchRequestWith("tag", COMMA_JOINER.join(tags));
	}
	
	private SearchRequest newSearchRequestWith(String key, String val) {
		Map<String,String> m=newHashMap(moo);
		m.put(key, val);
		return new SearchRequest(hitter, ImmutableMap.copyOf(m));
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
		return newSearchRequestWith("to-date", DATE_FORMAT.print(end));
	}

	public SearchRequest from(DateTime start) {
		return newSearchRequestWith("from-date", DATE_FORMAT.print(start));
	}

	public SearchRequest showFields(String... fields) {
		return newSearchRequestWith("show-fields", COMMA_JOINER.join(fields));
	}

	public SearchRequest showTags(String... tagTypes) {
		return newSearchRequestWith("show-tags", COMMA_JOINER.join(tagTypes));
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
		return newSearchRequestWith("page-size", ""+itemsPerPage);
	}

	public SearchRequest page(int page) {
		return newSearchRequestWith("page", ""+page);
	}


}
