package com.madgag.guardian.contentapi.jaxb;

import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="response")
public class SearchResponse {
	
	private static final JAXBContext JAXB_CONTEXT = createJaxbContext();
	
	@XmlAttribute
	public String status;
	
	@XmlAttribute
	public int total;
	
	@XmlAttribute(name="start-index")
	public int startIndex;
	
	@XmlElementWrapper(name="results")
	@XmlElement(name="content")
	public List<Content> contents;

	private static JAXBContext createJaxbContext() {
		try {
			return JAXBContext.newInstance(SearchResponse.class);
		} catch (JAXBException e) {
			throw new RuntimeException();
		}
	}
	
	public static SearchResponse unmarshall(InputStream inputStream) throws JAXBException {
		return (SearchResponse) createUnmarshaller().unmarshal(inputStream);
	}

	private static Unmarshaller createUnmarshaller() {
		try {
			return SearchResponse.JAXB_CONTEXT.createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException();
		}
	}

}
