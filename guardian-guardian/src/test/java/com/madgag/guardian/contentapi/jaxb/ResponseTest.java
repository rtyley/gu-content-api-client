package com.madgag.guardian.contentapi.jaxb;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.junit.Test;


public class ResponseTest {
	@Test
	public void shouldParseResponseXml() throws Exception {
		String responseXml="<response status=\"ok\" total=\"374\" start-index=\"1\" user-tier=\"free\"\n" + 
				"	page-size=\"10\" current-page=\"1\" pages=\"38\" order-by=\"newest\">\n" + 
				"	<results>\n" + 
				"		<content id=\"music/2010/mar/25/gavin-friday-virgin-prunes\"\n" + 
				"			section-id=\"music\" section-name=\"Music\" web-publication-date=\"2010-03-25T22:10:00Z\"\n" + 
				"			web-title=\"Gavin Friday: 'You can't be what you were'\"\n" + 
				"			web-url=\"http://www.guardian.co.uk/music/2010/mar/25/gavin-friday-virgin-prunes\"\n" + 
				"			api-url=\"http://content.guardianapis.com/music/2010/mar/25/gavin-friday-virgin-prunes\">\n" + 
				"			<fields>\n" + 
				"				<field name=\"headline\">Gavin Friday: 'You can't be what you were'</field>\n" + 
				"				<field name=\"publication\">The Guardian</field>\n" + 
				"			</fields>\n" + 
				"			<tags>\n" + 
				"				<tag type=\"keyword\" web-title=\"Pop and rock\" id=\"music/popandrock\"\n" + 
				"					api-url=\"http://content.guardianapis.com/music/popandrock\" web-url=\"http://www.guardian.co.uk/music/popandrock\"\n" + 
				"					section-id=\"music\" section-name=\"Music\" />\n" + 
				"			</tags>\n" + 
				"		</content>\n" + 
				"	</results>\n" + 
				"</response>";
		Response response = (Response) JAXBContext.newInstance(Response.class).createUnmarshaller().unmarshal(new StringReader(responseXml));
		assertThat(response.status, equalTo("ok"));
		assertThat(response.total, equalTo(374));
		assertThat(response.startIndex, equalTo(1));
		assertThat(response.contents.size(),equalTo(1));
	}
	

	@Test
	public void booYah() throws JAXBException, IOException {
		URL url = new URL("http://content.guardianapis.com/search?from-date=2010-03-25&to-date=2010-03-25&format=xml&show-fields=short-url&page-size=20");
		Response response = (Response) JAXBContext.newInstance(Response.class).createUnmarshaller().unmarshal(url.openStream());
		System.out.println(response.total);
	}
}
