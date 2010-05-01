package com.madgag.guardian.contentapi.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.madgag.guardian.contentapi.ApiResponse;
import com.madgag.guardian.contentapi.PageRequest;

@XmlRootElement(name="response")
public class PageResponse extends ApiResponse<PageRequest,PageResponse> {
	public static final JAXBContext JAXB_CONTEXT = JAXBUtil.createJAXBContextFor(PageResponse.class);
	
	@XmlElement
	public Content content;

}
