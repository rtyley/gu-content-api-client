package com.madgag.guardian.guardian;

import static com.google.common.collect.Iterables.partition;
import static com.madgag.guardian.guardian.MyGuiceServletContextListener.INJECTOR;
import static org.joda.time.Period.days;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.madgag.appengine.taskqueue.Deferrable;
import com.madgag.appengine.taskqueue.TaskQueueDeferrer;
import com.madgag.guardian.contentapi.jaxb.Content;

public class ArticleSpomCandidateFindingTask implements Deferrable {

	private static final long serialVersionUID = 1L;

	private final Content preferredMaster;

	public ArticleSpomCandidateFindingTask(Content preferredMaster) {
		this.preferredMaster = preferredMaster;
	}

	@Override
	public void run() {
		ArticleChronologyStore articleChronologyStore = INJECTOR.getInstance(ArticleChronologyStore.class);
		TaskQueueDeferrer deferrer = INJECTOR.getInstance(TaskQueueDeferrer.class);
		
		DateTime webPubDate = preferredMaster.webPublicationDate;
		Interval interval = new Interval(webPubDate.minus(days(2)), webPubDate);
		for (List<String> chunkIds : partition(articleChronologyStore.getArticleIdsFor(interval), 50)) {						
			deferrer.defer(new ArticleSpomSearch(preferredMaster.getId(), chunkIds),"deferredArticleSpomSearch");
		}
	}
}
