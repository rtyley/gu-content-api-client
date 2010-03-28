package com.madgag.guardian.contentapi;

@SuppressWarnings("serial")
public class ContentApiException extends RuntimeException {

	public ContentApiException(Exception e) {
		super(e);
	}

}
