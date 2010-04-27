package com.madgag.guardian.guardian;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.Interval;

import com.google.appengine.api.labs.taskqueue.TaskHandle;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.madgag.appengine.taskqueue.Deferrer;
import com.madgag.appengine.taskqueue.TaskQueueDeferrer;

@SuppressWarnings("serial")
@Singleton
public class BulkSearchServlet extends HttpServlet {
	
	private final Deferrer<TaskHandle> deferrer;

	@Inject
	public BulkSearchServlet(TaskQueueDeferrer deferrer) {
		this.deferrer = deferrer;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String intervalString=req.getParameter("interval");
		Interval interval = new Interval(intervalString);
		deferrer.defer(new IncrementalBulkSearchTask(interval));
		resp.getWriter().write("Will check articles during "+interval);
	}

}
