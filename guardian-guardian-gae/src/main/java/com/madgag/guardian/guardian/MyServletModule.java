package com.madgag.guardian.guardian;

import com.google.inject.servlet.ServletModule;
import com.madgag.appengine.taskqueue.DeferrableExecutingServlet;

public class MyServletModule extends ServletModule {
	@Override
	protected void configureServlets() {
		serve("/search").with(MyServlet.class);
		serve("/diff").with(DiffServlet.class);
		serve("/_ah/queue/deferred*").with(DeferrableExecutingServlet.class);
		

		serve("/worker/bulkSearch").with(BulkSearchServlet.class);
//		serve("/worker/identifySpoms").with(SpomIdentificationTaskServlet.class);
	}
}