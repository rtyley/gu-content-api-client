package com.madgag.guardian.contentapi.jaxb;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Multimaps.index;

import java.net.URI;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.DateTime;

import com.google.common.base.Function;
import com.google.common.collect.Multimap;

@XmlRootElement
public class Content {
	
	@XmlAttribute(name="web-title")
	public String webTitle;

	@XmlAttribute
	public String id;

	@XmlAttribute(name="api-url")
	public String apiUrl;

	@XmlJavaTypeAdapter(StringUriAdapter.class)
	@XmlAttribute(name="web-url")
	public URI webUrl;
	
	@XmlElementWrapper(name="tags")
	@XmlElement(name="tag")
	public List<Tag> tags=newArrayList();
	
	@XmlElementWrapper(name="fields")
	@XmlElement(name="field")
	public List<Field> fields=newArrayList();

	@XmlJavaTypeAdapter(BooBoo.class) // watch out for silent failures of adaptor marshalling...
	@XmlAttribute(name="web-publication-date", required=true)
	public DateTime webPublicationDate;

	public String getField(String fieldName) {
		for (Field field:fields) {
			if (fieldName.equals(field.name)) {
				return field.value;
			}
		}
		return null;
	}

	public boolean hasField(String fieldName) {
		return getField(fieldName)!=null;
	}
	
	public Multimap<String, Tag> getTagsCategorisedByType() {
		return index(tags, new Function<Tag, String>() {
			public String apply(Tag from) { return from.type; }
		});
	}
}
