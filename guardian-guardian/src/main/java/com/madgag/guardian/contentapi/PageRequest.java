package com.madgag.guardian.contentapi;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import javax.xml.bind.JAXBContext;

import com.madgag.guardian.contentapi.jaxb.PageResponse;

public class PageRequest extends ApiRequest<PageRequest,PageResponse> implements ContentRequest<PageRequest> {
	
	Map<String,String> moo=newHashMap();
	private final String id;

	public PageRequest(String id, Hitter hitter) {
		super(hitter);
		this.id = id;
	}

	@Override
	public Map<String, String> getParams() {
		return moo;
	}

	@Override
	public String getPathPrefix() {
		return id;
	}

	public PageRequest showFields(String... fields) {
		moo.put("show-fields", COMMA_JOINER.join(fields));
		return this;
	}
	
	@Override
	public PageRequest showTags(String... tagTypes) {
		moo.put("show-tags", COMMA_JOINER.join(tagTypes));
		return this;
	}

	public JAXBContext getJaxbContextForResponse() {
		return PageResponse.JAXB_CONTEXT;
	}


}
