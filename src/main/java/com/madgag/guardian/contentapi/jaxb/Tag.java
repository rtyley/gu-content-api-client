package com.madgag.guardian.contentapi.jaxb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Tag implements Serializable {

	private static final long serialVersionUID = 1L;

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

	@Override
	public boolean equals(Object o) {
		Tag otherTag=(Tag) o;
		return id.equals(otherTag.id);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public String toString() {
		return type+":"+id;
	}
	
}
