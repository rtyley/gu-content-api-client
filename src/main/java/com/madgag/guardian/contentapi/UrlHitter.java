package com.madgag.guardian.contentapi;

public interface UrlHitter {

	public abstract <Req extends ApiRequest<Req, Resp>, Resp extends ApiResponse<Req, Resp>> Resp makeWebRequestFor(
			Req apiRequest);

	public abstract ApiConfig getConfig();

}