package com.madgag.guardian.contentapi;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.google.inject.Inject;
import com.madgag.guardian.contentapi.jaxb.SearchResponse;

public class Hitter {
	
	private final ApiRequestUrlGenerator urlGenerator;
	
	@Inject
	public Hitter(ApiRequestUrlGenerator urlGenerator) {
		this.urlGenerator = urlGenerator;
	}
	
	public <T> T jojo(ApiRequest<T> apiRequest) throws IOException, JAXBException {
		return (T) SearchResponse.createUnmarshaller().unmarshal(urlGenerator.urlFor(apiRequest).openStream());
	}
}
