package com.madgag.guardian.contentapi;

import java.util.Map;

import javax.xml.bind.JAXBContext;

public interface ApiRequest<T> {
	
	public String getPathPrefix();
	
	public Map<String,String> getParams();

	public JAXBContext getJaxbContextForResponse();
	
	public T execute();
	
}
