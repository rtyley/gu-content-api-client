package com.madgag.guardian.guardian;

import static com.madgag.guardian.guardian.MyGuiceServletContextListener.INJECTOR;
import static com.madgag.guardian.guardian.PageProcessingProgress.noContentYetProcessedForPage;

import java.util.logging.Logger;

import org.joda.time.Interval;

import com.google.apphosting.api.DeadlineExceededException;
import com.madgag.appengine.taskqueue.Deferrable;
import com.madgag.appengine.taskqueue.Deferrer;
import com.madgag.guardian.contentapi.jaxb.Content;
import com.madgag.guardian.contentapi.jaxb.SearchResponse;
import com.madgag.guardian.guardian.spom.detection.reporting.TwitterReporter;

public class IncrementalBulkSearchTask implements Deferrable {
	private static final Logger log = Logger.getLogger(TwitterReporter.class.getName());

	private static final long serialVersionUID = 1L;
	
	private final ArticleChronology articleChronology;
	private final Interval targetArticleInterval;
	
	private PageProcessingProgress pageProcessingProgress;

	IncrementalBulkSearchTask(
			Interval targetArticleInterval,
			PageProcessingProgress pageProcessingProgress,
			ArticleChronology articleChronology) {
		this.articleChronology = articleChronology;
		this.targetArticleInterval = targetArticleInterval;
		this.pageProcessingProgress = pageProcessingProgress;
	}

	public IncrementalBulkSearchTask(Interval targetArticleInterval) {
		this(targetArticleInterval,noContentYetProcessedForPage(1),new ArticleChronology());
	}

	@Override
	public void run() {
		try {
			processAsMuchAsPossible();
		} catch (DeadlineExceededException e) {
			deferForABrighterTomorrow();
		}
	}

	private void deferForABrighterTomorrow() {
		INJECTOR.getInstance(Deferrer.class).defer(new IncrementalBulkSearchTask(targetArticleInterval,pageProcessingProgress,articleChronology));
	}

	private void processAsMuchAsPossible() {
		IncrementalBulkSearchProcessor processor = INJECTOR.getInstance(IncrementalBulkSearchProcessor.class);
		SearchResponse searchResultsPage = processor.createSearchRequestFor(targetArticleInterval).page(pageProcessingProgress.getPage()).execute();
		for (int batchNum=0; batchNum<2; ++batchNum) {
			log.info("Processing page "+searchResultsPage.currentPage+" out of "+searchResultsPage.pages+" for "+targetArticleInterval);
			for (Content content : searchResultsPage.contents) {
				if (pageProcessingProgress.hasNotYetProcessed(content.id)) {
					processor.process(content,targetArticleInterval,articleChronology);
					pageProcessingProgress = pageProcessingProgress.updatedWith(content.id);
				}
			}
			pageProcessingProgress = noContentYetProcessedForPage(searchResultsPage.currentPage+1);
			if (!searchResultsPage.hasNext()) {
				return; // WE ARE FINISHED!
			}
			searchResultsPage = searchResultsPage.next();
		}
		deferForABrighterTomorrow();
	}
	
}
