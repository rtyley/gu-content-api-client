package com.madgag.guardian.guardian;

import java.io.Serializable;

import com.google.common.collect.ImmutableSet;

public class PageProcessingProgress implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final int page;
	private final ImmutableSet<String> processedContentIds;
	
	private PageProcessingProgress(int page, ImmutableSet<String> processedContentIds) {
		this.page = page;
		this.processedContentIds = processedContentIds;
	}
	
	public static PageProcessingProgress noContentYetProcessedForPage(int page) {
		return new PageProcessingProgress(page, ImmutableSet.<String>of());
	}

	public PageProcessingProgress updatedWith(String processedContentId) {
		return new PageProcessingProgress(page, ImmutableSet.<String>builder().addAll(processedContentIds).add(processedContentId).build());
	}

	public boolean hasNotYetProcessed(String contentId) {
		return !processedContentIds.contains(contentId);
	}

	public int getPage() {
		return page;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+"[page="+page+"]";
	}
}
