package com.madgag.guardian.contentapi.jaxb;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;

import org.junit.Test;


public class ContentTest {
	@Test
	public void shouldParseContentXml() throws Exception {
		String contentXml="<content" +
				" id=\"music/2010/mar/25/gavin-friday-virgin-prunes\"" +
				" section-id=\"music\"" +
				" section-name=\"Music\"" +
				" web-publication-date=\"2010-03-25T22:10:00Z\"" +
				" web-title=\"Gavin Friday: 'You can't be what you were'\"" +
				" web-url=\"http://www.guardian.co.uk/music/2010/mar/25/gavin-friday-virgin-prunes\"" +
				" api-url=\"http://content.guardianapis.com/music/2010/mar/25/gavin-friday-virgin-prunes\">" +
				"<fields><field name=\"headline\">Gavin Friday: 'You can't be what you were'</field>" +
				"<field name=\"trail-text\">How do you move on from being Dublin's rock'n'roll Lucifer? By becoming U2's 'aesthetic midwife', outdressing 50 Cent and roping in the Salvation Army for your latest album. Mike Atkinson meets &lt;strong&gt;Gavin Friday&lt;/strong&gt;</field>" +
				"<field name=\"short-url\">http://gu.com/p/2fznm</field>" +
				"<field name=\"standfirst\">How do you move on from being Dublin's rock'n'roll Lucifer? By becoming U2's 'aesthetic midwife', outdressing 50 Cent and roping in the Salvation Army for your latest album. Mike Atkinson meets Gavin Friday</field>" +
				"<field name=\"thumbnail\">http://static.guim.co.uk/sys-images/Arts/Arts_/Pictures/2010/3/24/1269445330239/Gavin-Friday-005.jpg</field><field name=\"byline\">Mike Atkinson</field><field name=\"publication\">The Guardian</field>" +
				"</fields>" +
				"<tags>" +
				"<tag type=\"keyword\" web-title=\"Pop and rock\" id=\"music/popandrock\" api-url=\"http://content.guardianapis.com/music/popandrock\" web-url=\"http://www.guardian.co.uk/music/popandrock\" section-id=\"music\" section-name=\"Music\"/>" +
				"<tag type=\"keyword\" web-title=\"U2\" id=\"music/u2\" api-url=\"http://content.guardianapis.com/music/u2\" web-url=\"http://www.guardian.co.uk/music/u2\" section-id=\"music\" section-name=\"Music\"/>" +
				"<tag type=\"type\" web-title=\"Article\" id=\"type/article\" api-url=\"http://content.guardianapis.com/articles\" web-url=\"http://www.guardian.co.uk/articles\"/>" +
				"</tags></content>";
		Content content = (Content) JAXBContext.newInstance(Content.class).createUnmarshaller().unmarshal(new StringReader(contentXml));
		assertThat(content.id, equalTo("music/2010/mar/25/gavin-friday-virgin-prunes"));
		assertThat(content.webTitle, equalTo("Gavin Friday: 'You can't be what you were'"));
		assertThat(content.apiUrl, equalTo("http://content.guardianapis.com/music/2010/mar/25/gavin-friday-virgin-prunes"));
		assertThat(content.webUrl, equalTo("http://www.guardian.co.uk/music/2010/mar/25/gavin-friday-virgin-prunes"));
		
		assertThat(content.getField("headline"), equalTo("Gavin Friday: 'You can't be what you were'"));
		assertThat(content.tags.size(), equalTo(3));
	}
	

}
