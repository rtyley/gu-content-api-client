package com.madgag.guardian.contentapi;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.junit.Test;

import com.madgag.guardian.contentapi.jaxb.Response;


public class HitterTest {
	@Test
	public void booYah() throws JAXBException, IOException {
		
		ApiRequestUrlGenerator apiRequestUrlGenerator = new ApiRequestUrlGenerator("http://content.guardianapis.com/");
		Hitter hitter = new Hitter(apiRequestUrlGenerator);
		SearchRequest apiRequest = new SearchRequest();
		apiRequest.setTags("film/comedy");
		apiRequest.setInterval(new Interval(Duration.standardDays(4), new Instant()));
		Response response = hitter.jojo(apiRequest);
		System.out.println(response.total);
	}
}
