package com.madgag.guardian.contentapi.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
public class Field {

	public static Field create(String name, String value) {
		Field field=new Field();
		field.name = name;
		field.value = value;
		return field;
	}

	@XmlAttribute
	public String name;

	@XmlValue
	public String value;

}
