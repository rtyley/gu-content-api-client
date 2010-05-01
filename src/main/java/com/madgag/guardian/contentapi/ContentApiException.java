package com.madgag.guardian.contentapi;

@SuppressWarnings("serial")
public class ContentApiException extends RuntimeException {

	public ContentApiException(Exception e) {
		super(e);
	}

	public ContentApiException(String string, Throwable e) {
		super(string, e);
	}

}
