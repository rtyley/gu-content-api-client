package com.madgag.guardian.contentapi.jaxb;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.uniqueIndex;

import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Function;
import com.madgag.guardian.contentapi.ApiResponse;
import com.madgag.guardian.contentapi.SearchRequest;

@XmlRootElement(name="response")
public class SearchResponse extends ApiResponse<SearchRequest,SearchResponse> {
	
	public static final JAXBContext JAXB_CONTEXT = JAXBUtil.createJAXBContextFor(SearchResponse.class);
	
	@XmlAttribute
	public String status;
	
	@XmlAttribute
	public int total;
	
	// <response status="ok" total="1141" start-index="7" user-tier="free" page-size="2" current-page="4" pages="571" order-by="newest">
	
	@XmlAttribute(name="start-index")
	public int startIndex;
	
	@XmlAttribute(name="current-page")
	public int currentPage;
	
	@XmlAttribute(name="pages")
	public int pages;
	
	@XmlElementWrapper(name="results")
	@XmlElement(name="content")
	public List<Content> contents=newArrayList();

	public Map<String,Content> getContentById() {
		return uniqueIndex(contents, new Function<Content, String>() {
			public String apply(Content c) { return c.id; }
		});
	}
	
	public SearchResponse next() {
		if (!hasNext()) {
			return null;
		}
		return getOriginalRequest().page(currentPage+1).execute();
	}

	public boolean hasNext() {
		return currentPage<pages;
	}


}
