package com.madgag.guardian.contentapi.jaxb;

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="response")
public class SearchResponse {
	
	public static final JAXBContext JAXB_CONTEXT = JAXBUtil.createJAXBContextFor(SearchResponse.class);
	
	@XmlAttribute
	public String status;
	
	@XmlAttribute
	public int total;
	
	@XmlAttribute(name="start-index")
	public int startIndex;
	
	@XmlElementWrapper(name="results")
	@XmlElement(name="content")
	public List<Content> contents;


}
