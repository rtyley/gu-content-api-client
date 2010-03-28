package com.madgag.guardian.contentapi.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="response")
public class PageResponse {
	public static final JAXBContext JAXB_CONTEXT = JAXBUtil.createJAXBContextFor(PageResponse.class);
	
	@XmlElement
	public Content content;

}
