package com.madgag.guardian.contentapi.jaxb;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;

import org.junit.Test;


public class FieldTest {
	@Test
	public void shouldParseFieldXml() throws Exception {
		String fieldXml="<field name=\"headline\">Gavin Friday: 'You can't be what you were'</field>";
		Field field = (Field) JAXBContext.newInstance(Field.class).createUnmarshaller().unmarshal(new StringReader(fieldXml));
		assertThat(field.value, equalTo("Gavin Friday: 'You can't be what you were'"));
		assertThat(field.name, equalTo("headline"));
	}
}
