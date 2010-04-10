package com.madgag.guardian.guardian;

import static com.google.appengine.api.labs.taskqueue.TaskOptions.Builder.url;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.Interval;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.repackaged.com.google.common.base.Joiner;
import com.google.inject.Singleton;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

@SuppressWarnings("serial")
@Singleton
public class SpomIdentificationTaskServlet extends HttpServlet {

	private static final Joiner COMMA_JOINER = Joiner.on(',');
	private final BulkSearchSpaceGenerator bulkSearchSpaceGenerator;
	
	public SpomIdentificationTaskServlet(BulkSearchSpaceGenerator bulkSearchSpaceGenerator) {
		this.bulkSearchSpaceGenerator = bulkSearchSpaceGenerator;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String intervalString=req.getParameter("interval");
		Interval interval = new Interval(intervalString);
		SearchSpace searchSpace = bulkSearchSpaceGenerator.getSearchSpaceCovering(interval);
		Queue queue = QueueFactory.getDefaultQueue();
		for (NormalisedArticle target : searchSpace.getArticlesToCheck()) {
			String possibleSpomIdsString = COMMA_JOINER.join(searchSpace.possibleSpomIdsFor(target));
			queue.add(url("/worker/identifySpoms").param("targetId", target.getId()).param("possibleSpomIds", possibleSpomIdsString));
		}
	}

}
