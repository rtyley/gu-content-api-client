package com.madgag.guardian.contentapi;

import static com.google.common.collect.ImmutableMap.copyOf;
import static com.google.common.collect.Maps.newLinkedHashMap;
import static java.util.Arrays.asList;
import static org.joda.time.DateTimeZone.UTC;

import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;

import org.joda.time.ReadableInstant;
import org.joda.time.ReadableInterval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.madgag.guardian.contentapi.jaxb.SearchResponse;

public class SearchRequest extends ApiRequest<SearchRequest,SearchResponse> implements ContentRequest<SearchRequest> {

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd").withZone(UTC);
	private static final Joiner COMMA_JOINER = Joiner.on(",");
	
	private final ImmutableMap<String,String> params;
	
	public SearchRequest(UrlHitter hitter) {
		super(hitter);
		params = ImmutableMap.of();
	}
	
	public SearchRequest(UrlHitter hitter, ImmutableMap<String,String> params) {
		super(hitter);
		this.params = params;
	}

	public SearchRequest withIds(Iterable<String> ids) {
		return newSearchRequestWith("ids", COMMA_JOINER.join(ids));
	}
	
	public SearchRequest withIds(String... ids) {
		return newSearchRequestWith("ids", COMMA_JOINER.join(ids));
	}
	
	public SearchRequest withTags(String... tags) {
		return newSearchRequestWith("tag", COMMA_JOINER.join(tags));
	}

	public SearchRequest to(ReadableInstant end) {
		return newSearchRequestWith("to-date", DATE_FORMAT.print(end));
	}

	public SearchRequest from(ReadableInstant start) {
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
	
	public SearchRequest pageSize(int itemsPerPage) {
		return newSearchRequestWith("page-size", ""+itemsPerPage);
	}

	public SearchRequest page(int page) {
		return newSearchRequestWith("page", ""+page);
	}
	
	//order-by=oldest
	public SearchRequest orderBy(String order) {
		return newSearchRequestWith("order-by", order);
	}

	
	private SearchRequest newSearchRequestWith(String key, String val) {
		Map<String,String> newParams=newLinkedHashMap(params);
		newParams.put(key, val);
		return new SearchRequest(hitter, copyOf(newParams));
	}
	
	
	
	
	public JAXBContext getJaxbContextForResponse() {
		return SearchResponse.JAXB_CONTEXT;
	}

	@Override
	protected Map<String, String> getParams() {
		return params;
	}

	@Override
	protected String getPathPrefix() {
		return "search";
	}

	public List<String> getIds() {
		return asList(params.get("ids").split(","));
	}

}
