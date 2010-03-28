package com.madgag.guardian.contentapi;

import java.net.URL;
import java.util.logging.Logger;

import com.google.inject.Inject;

public class Hitter {

	private static final Logger log = Logger.getLogger(Hitter.class.getName());

	private final ApiRequestUrlGenerator urlGenerator;

	@Inject
	public Hitter(ApiRequestUrlGenerator urlGenerator) {
		this.urlGenerator = urlGenerator;
	}

	public <T> T jojo(ApiRequest<T> apiRequest) {
		try {
			URL url = urlGenerator.urlFor(apiRequest);
			log.info(url.toString());
			return (T) apiRequest.getJaxbContextForResponse().createUnmarshaller().unmarshal(url.openStream());
		} catch (Exception e) {
			throw new ContentApiException(e);
		}
	}

}
