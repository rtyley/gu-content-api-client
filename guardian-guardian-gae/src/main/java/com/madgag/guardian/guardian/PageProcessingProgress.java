package com.madgag.guardian.guardian;

import static com.google.common.collect.Iterables.filter;

import java.io.Serializable;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.madgag.guardian.contentapi.jaxb.Content;

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

	public Iterable<Content> contentNotYetProcessedFrom(Iterable<Content> contentItems) {
		return filter(contentItems, new Predicate<Content>() {
			public boolean apply(Content input) { return processedContentIds.contains(input.getId()); }
		});
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
