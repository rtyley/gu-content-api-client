package com.madgag.guardian.contentapi.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Tag {

	@XmlAttribute
	public String type;
	
	@XmlAttribute(name="web-title")
	public String webTitle;

	@XmlAttribute
	public String id;

	@XmlAttribute(name="api-url")
	public String apiUrl;

	@XmlAttribute(name="web-url")
	public String webUrl;

}
