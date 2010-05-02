package com.madgag.guardian.contentapi;

import static com.google.common.collect.Maps.newHashMap;

import java.net.URI;
import java.util.Map;

import javax.xml.bind.JAXBContext;

import com.google.common.base.Joiner;
import com.google.common.base.Joiner.MapJoiner;

public abstract class ApiRequest<Req extends ApiRequest<Req,Resp>, Resp extends ApiResponse<Req,Resp>> {
	protected static final Joiner COMMA_JOINER = Joiner.on(",");
	private static final MapJoiner QUERY_PARAMS_JOINER = Joiner.on("&").withKeyValueSeparator("=");

	protected final UrlHitter hitter;
	
	protected ApiRequest(UrlHitter hitter) {
		this.hitter = hitter;
	}
	
	abstract String getPathPrefix();
	
	abstract Map<String,String> getParams();

	public abstract JAXBContext getJaxbContextForResponse();
	
	@SuppressWarnings("unchecked")
	public final Resp execute() {
		return hitter.makeWebRequestFor((Req)this);
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
