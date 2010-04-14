package com.madgag.guardian.guardian;

import static com.madgag.guardian.guardian.MyGuiceServletContextListener.INJECTOR;
import static com.madgag.guardian.guardian.PageProcessingProgress.noContentYetProcessedForPage;
import static com.newatlanta.appengine.taskqueue.Deferred.defer;

import java.io.IOException;
import java.util.SortedMap;

import javax.servlet.ServletException;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.google.apphosting.api.DeadlineExceededException;
import com.google.common.collect.Maps;
import com.madgag.guardian.contentapi.jaxb.Content;
import com.madgag.guardian.contentapi.jaxb.SearchResponse;
import com.newatlanta.appengine.taskqueue.Deferred.Deferrable;

public class IncrementalBulkSearchTask implements Deferrable {

	private static final long serialVersionUID = 1L;
	
	private final SortedMap<DateTime, String> searchSpaceArticleIds;
	private final Interval targetArticleInterval;
	
	private PageProcessingProgress pageProcessingProgress;

	IncrementalBulkSearchTask(
			Interval targetArticleInterval,
			PageProcessingProgress pageProcessingProgress,
			SortedMap<DateTime, String> searchSpaceArticleIds) {
		this.searchSpaceArticleIds = searchSpaceArticleIds;
		this.targetArticleInterval = targetArticleInterval;
		this.pageProcessingProgress = pageProcessingProgress;
	}

	public IncrementalBulkSearchTask(Interval targetArticleInterval) {
		this(targetArticleInterval,noContentYetProcessedForPage(1),Maps.<DateTime, String>newTreeMap());
	}

	@Override
	public void doTask() throws ServletException, IOException {
		try {
			processAsMuchAsPossible();
		} catch (DeadlineExceededException e) {
			defer(new IncrementalBulkSearchTask(targetArticleInterval,pageProcessingProgress,searchSpaceArticleIds));
		}
	}

	private void processAsMuchAsPossible() {
		IncrementalBulkSearchProcessor processor = INJECTOR.getInstance(IncrementalBulkSearchProcessor.class);
		SearchResponse searchResultsPage = processor.createSearchRequestFor(targetArticleInterval).page(pageProcessingProgress.getPage()).execute();
		while (searchResultsPage != null) {
			for (Content content : searchResultsPage.contents) {
				if (pageProcessingProgress.hasNotYetProcessed(content.id)) {
					processor.process(content,targetArticleInterval,searchSpaceArticleIds);
					pageProcessingProgress = pageProcessingProgress.updatedWith(content.id);
				}
			}
			pageProcessingProgress = noContentYetProcessedForPage(searchResultsPage.currentPage+1);
			searchResultsPage = searchResultsPage.next();
		}
	}
	
}
