package com.madgag.guardian.contentapi;

import java.net.URI;
import java.net.URL;
import java.util.logging.Logger;

import com.google.inject.Inject;

public class Hitter {

	private static final Logger log = Logger.getLogger(Hitter.class.getName());

	private final ApiConfig apiConfig;

	@Inject
	public Hitter(ApiConfig urlGenerator) {
		this.apiConfig = urlGenerator;
	}

	@SuppressWarnings("unchecked")
	public <Req extends ApiRequest<Req, Resp>, Resp extends ApiResponse<Req, Resp>> Resp jojo(Req apiRequest) {
		URI uri = apiRequest.toUri();
		try {
			URL url = uri.toURL();
			log.info(url.toString());
			Resp response = (Resp) apiRequest.getJaxbContextForResponse().createUnmarshaller().unmarshal(url.openStream());
			response.setOriginalRequest(apiRequest);
			return response;
		} catch (Exception e) {
			throw new ContentApiException("Error reading from "+uri,e);
		}
	}

	public ApiConfig getConfig() {
		return apiConfig;
	}

}
