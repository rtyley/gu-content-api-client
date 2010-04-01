package com.madgag.guardian.contentapi;

import static com.google.common.collect.Maps.newHashMap;

import java.net.URI;
import java.util.Map;

import javax.xml.bind.JAXBContext;

import com.google.common.base.Joiner;
import com.google.common.base.Joiner.MapJoiner;

public abstract class ApiRequest<T extends ApiResponse> {
	protected static final Joiner COMMA_JOINER = Joiner.on(",");
	private static final MapJoiner QUERY_PARAMS_JOINER = Joiner.on("&").withKeyValueSeparator("=");

	protected final Hitter hitter;
	
	protected ApiRequest(Hitter hitter) {
		this.hitter = hitter;
	}
	
	abstract String getPathPrefix();
	
	abstract Map<String,String> getParams();

	public abstract JAXBContext getJaxbContextForResponse();
	
	public final T execute() {
		return hitter.jojo(this);
	}
	
	public URI toUri() {
		ApiConfig config = hitter.getConfig();
		Map<String,String> params = newHashMap(getParams());
		params.put("format", "xml");
		if (config.getApiKey()!=null) {
			params.put("api-key",config.getApiKey());
		}
		return URI.create(config.getServerPath()+getPathPrefix()+"?"+QUERY_PARAMS_JOINER.join(params));
	}
	
}
