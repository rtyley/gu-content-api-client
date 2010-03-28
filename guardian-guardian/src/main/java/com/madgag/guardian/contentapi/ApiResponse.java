package com.madgag.guardian.contentapi;

public abstract class ApiResponse<T extends ApiRequest<?>> {
	
	private T originalRequest;
	
	void setOriginalRequest(T originalRequest) {
		this.originalRequest = originalRequest;
	}
	
	public T getOriginalRequest() {
		return originalRequest;
	}
	
}
