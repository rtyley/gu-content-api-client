package com.madgag.guardian.contentapi;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.madgag.guardian.contentapi.jaxb.Response;

public class Hitter {
	
	private final ApiRequestUrlGenerator urlGenerator;
	
	public Hitter(ApiRequestUrlGenerator urlGenerator) {
		this.urlGenerator = urlGenerator;
	}
	
	public Response jojo(ApiRequest apiRequest) throws IOException, JAXBException {
		return Response.unmarshall(urlGenerator.urlFor(apiRequest).openStream());
	}
}
