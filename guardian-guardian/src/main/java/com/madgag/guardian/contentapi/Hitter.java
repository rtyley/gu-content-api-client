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
	
	public SearchResponse jojo(ApiRequest apiRequest) throws IOException, JAXBException {
		return SearchResponse.unmarshall(urlGenerator.urlFor(apiRequest).openStream());
	}
}
