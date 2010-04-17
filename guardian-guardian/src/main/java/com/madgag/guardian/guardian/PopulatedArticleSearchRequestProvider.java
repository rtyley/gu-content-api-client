package com.madgag.guardian.guardian;

import com.google.inject.Inject;
import com.madgag.guardian.contentapi.ContentApiClient;
import com.madgag.guardian.contentapi.ContentRequest;
import com.madgag.guardian.contentapi.PageRequest;
import com.madgag.guardian.contentapi.SearchRequest;

public class PopulatedArticleSearchRequestProvider {
	
	private final ContentApiClient apiClient;
	
	@Inject
	public PopulatedArticleSearchRequestProvider(ContentApiClient apiClient) {
		this.apiClient = apiClient;
	}
	
	public SearchRequest articleSearch() {
		return showRequiredData(apiClient.search()).withTags("type/article").orderBy("oldest");
	}
	
	public PageRequest contentWithId(String id) {
		return showRequiredData(apiClient.loadPageWith(id));
	}
	
	private <Req extends ContentRequest<Req>> Req showRequiredData(ContentRequest<Req> request) {
		return request.showFields("body","short-url").showTags("all");
	}

}
