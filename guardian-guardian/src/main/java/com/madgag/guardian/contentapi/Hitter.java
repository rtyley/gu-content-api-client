package com.madgag.guardian.contentapi;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import com.google.inject.Inject;
import com.madgag.guardian.contentapi.jaxb.SearchResponse;

public class Hitter {
	
	private static final Logger log = Logger.getLogger(Hitter.class.getName());
	
	private final ApiRequestUrlGenerator urlGenerator;
	
	@Inject
	public Hitter(ApiRequestUrlGenerator urlGenerator) {
		this.urlGenerator = urlGenerator;
	}
	
	public <T> T jojo(ApiRequest<T> apiRequest) throws IOException, JAXBException {
		URL url = urlGenerator.urlFor(apiRequest);
		log.info(url.toString());
		return (T) SearchResponse.createUnmarshaller().unmarshal(url.openStream());
	}
}
