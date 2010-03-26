package com.madgag.guardian.contentapi;

import static org.joda.time.Duration.standardDays;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.joda.time.Instant;
import org.joda.time.Interval;
import org.junit.Test;

import com.madgag.guardian.contentapi.jaxb.SearchResponse;


public class HitterTest {
	@Test
	public void shouldBeAbleToHitTheAPI() throws JAXBException, IOException {
		ApiRequestUrlGenerator apiRequestUrlGenerator = new ApiRequestUrlGenerator("http://content.guardianapis.com/");
		Hitter hitter = new Hitter(apiRequestUrlGenerator);
		SearchRequest apiRequest = new SearchRequest();
		apiRequest.setTags("film/comedy");
		apiRequest.setInterval(new Interval(standardDays(4), new Instant()));
		SearchResponse response = hitter.jojo(apiRequest);
		System.out.println(response.total);
	}
}
