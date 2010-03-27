package com.madgag.guardian.contentapi;

import static java.util.Collections.emptyMap;

import java.util.Map;

import com.madgag.guardian.contentapi.jaxb.PageResponse;

public class PageRequest implements ApiRequest<PageResponse> {
	
	private final String id;

	public PageRequest(String id) {
		this.id = id;
	}

	@Override
	public Map<String, String> getParams() {
		return emptyMap();
	}

	@Override
	public String getPathPrefix() {
		return id;
	}
}
