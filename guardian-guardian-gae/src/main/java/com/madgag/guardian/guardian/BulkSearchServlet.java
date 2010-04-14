package com.madgag.guardian.guardian;

import static com.newatlanta.appengine.taskqueue.Deferred.defer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.Interval;

import com.google.inject.Singleton;

@SuppressWarnings("serial")
@Singleton
public class BulkSearchServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String intervalString=req.getParameter("interval");
		Interval interval = new Interval(intervalString);
		defer(new IncrementalBulkSearchTask(interval));
		resp.getWriter().write("Will check articles during "+interval);
	}

}
