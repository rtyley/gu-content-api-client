package com.madgag.guardian.contentapi;

import static com.google.common.collect.Maps.newHashMap;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import javax.xml.bind.JAXBException;

import com.google.common.base.Joiner;
import com.google.common.base.Joiner.MapJoiner;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class ApiRequestUrlGenerator {
	
	private static MapJoiner QUERY_PARAMS_JOINER = Joiner.on("&").withKeyValueSeparator("=");
	
	private final String apiKey;
	private final String serverPath;

	public ApiRequestUrlGenerator(String serverPath) {
		this(serverPath,null);
	}
	
	@Inject
	public ApiRequestUrlGenerator(
			@Named("guardian-content-api.server-path") String serverPath, 
			@Named("guardian-content-api.key") String apiKey) {
		this.apiKey = apiKey;
		this.serverPath = serverPath;
	}
	
	public URL urlFor(ApiRequest apiRequest) throws IOException, JAXBException {
		Map<String,String> params = newHashMap(apiRequest.getParams());
		params.put("format", "xml");
		if (apiKey!=null) {
			params.put("api-key",apiKey);
		}
		return new URL(serverPath+apiRequest.getPathPrefix()+"?"+QUERY_PARAMS_JOINER.join(params));
	}
}
