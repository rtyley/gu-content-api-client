package com.madgag.guardian.contentapi.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Content {

	@XmlAttribute(name="web-title")
	public String webTitle;

	@XmlAttribute
	public String id;

	@XmlAttribute(name="api-url")
	public String apiUrl;

	@XmlAttribute(name="web-url")
	public String webUrl;
	
	@XmlElementWrapper(name="tags")
	@XmlElement(name="tag")
	public List<Tag> tags;
	
	@XmlElementWrapper(name="fields")
	@XmlElement(name="field")
	public List<Field> fields;

	public String getField(String fieldName) {
		for (Field field:fields) {
			if (fieldName.equals(field.name)) {
				return field.value;
			}
		}
		return null;
	}
}
