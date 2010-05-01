package com.madgag.guardian.contentapi.jaxb;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;

import org.junit.Test;


public class TagTest {
	@Test
	public void shouldParseTagXml() throws Exception {
		String tagXml="<tag type=\"keyword\" web-title=\"Pop and rock\" id=\"music/popandrock\" api-url=\"http://content.guardianapis.com/music/popandrock\" web-url=\"http://www.guardian.co.uk/music/popandrock\" section-id=\"music\" section-name=\"Music\"/>";
		Tag tag = (Tag) JAXBContext.newInstance(Tag.class).createUnmarshaller().unmarshal(new StringReader(tagXml));
		assertThat(tag.type, equalTo("keyword"));
		assertThat(tag.webTitle, equalTo("Pop and rock"));
		assertThat(tag.id, equalTo("music/popandrock"));
		assertThat(tag.apiUrl, equalTo("http://content.guardianapis.com/music/popandrock"));
		assertThat(tag.webUrl, equalTo("http://www.guardian.co.uk/music/popandrock"));
	}
}
