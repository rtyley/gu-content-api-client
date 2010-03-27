package com.madgag.guardian.contentapi;

import java.util.Map;

public interface ApiRequest<T> {
	
	public String getPathPrefix();
	
	public Map<String,String> getParams();
	
}
