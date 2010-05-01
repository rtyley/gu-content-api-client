package com.madgag.guardian.contentapi;

public abstract class ApiResponse<Req extends ApiRequest<Req,Resp>, Resp extends ApiResponse<Req,Resp>> {
	
	private Req originalRequest;
	
	void setOriginalRequest(Req originalRequest) {
		this.originalRequest = originalRequest;
	}
	
	public Req getOriginalRequest() {
		return originalRequest;
	}
	
}
