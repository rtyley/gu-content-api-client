package com.madgag.guardian.contentapi;

import com.google.inject.Inject;


public class ContentApiClient {

	private final UrlHitter hitter;

    public ContentApiClient() {
        this(new JavaNetUrlHitter(new ApiConfig("http://content.guardianapis.com/")));
    }

	@Inject
	public ContentApiClient(UrlHitter hitter) {
		this.hitter = hitter;
	}
	
	public SearchRequest search() {
		return new SearchRequest(hitter);
	}

	public PageRequest loadPageWith(String id) {
		return new PageRequest(id, hitter);
	}
}
