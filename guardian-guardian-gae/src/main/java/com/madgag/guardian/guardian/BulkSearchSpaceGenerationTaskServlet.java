package com.madgag.guardian.guardian;

import static com.newatlanta.appengine.taskqueue.Deferred.defer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.Interval;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;

@SuppressWarnings("serial")
@Singleton
public class BulkSearchSpaceGenerationTaskServlet extends HttpServlet {

	private final BulkSearchSpaceGenerator bulkSearchSpaceGenerator;
	
	@Inject
	public BulkSearchSpaceGenerationTaskServlet(BulkSearchSpaceGenerator bulkSearchSpaceGenerator) {
		this.bulkSearchSpaceGenerator = bulkSearchSpaceGenerator;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String intervalString=req.getParameter("interval");
		Interval interval = new Interval(intervalString);
		SearchSpace searchSpace = bulkSearchSpaceGenerator.getSearchSpaceCovering(interval);
		for (NormalisedArticle target : searchSpace.getArticlesToCheck()) {
			defer(new ArticleSpomSearch(target.getId(),searchSpace.possibleSpomIdsFor(target)));
		}
		resp.getWriter().write("Will check "+searchSpace.getArticlesToCheck().size()+" articles");
	}

}
