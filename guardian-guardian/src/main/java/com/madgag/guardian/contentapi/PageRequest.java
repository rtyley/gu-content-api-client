package com.madgag.guardian.contentapi;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import javax.xml.bind.JAXBContext;

import com.google.common.base.Joiner;
import com.madgag.guardian.contentapi.jaxb.PageResponse;

public class PageRequest implements ApiRequest<PageResponse> {
	
	private static final Joiner COMMA_JOINER = Joiner.on(",");
	Map<String,String> moo=newHashMap();
	private final String id;
	private final Hitter hitter;
	

	public PageRequest(String id, Hitter hitter) {
		this.id = id;
		this.hitter = hitter;
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

	public JAXBContext getJaxbContextForResponse() {
		return PageResponse.JAXB_CONTEXT;
	}

	@Override
	public PageResponse execute() {
		return hitter.jojo(this);
	}
}
