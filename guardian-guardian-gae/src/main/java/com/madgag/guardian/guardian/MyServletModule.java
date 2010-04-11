package com.madgag.guardian.guardian;

import com.google.inject.servlet.ServletModule;
import com.newatlanta.appengine.taskqueue.Deferred;

public class MyServletModule extends ServletModule {
	@Override
	protected void configureServlets() {
		serve("/search").with(MyServlet.class);
		serve("/_ah/queue/deferred").with(Deferred.class);
		bind(Deferred.class).asEagerSingleton();
		

		serve("/worker/bulkSearch").with(BulkSearchSpaceGenerationTaskServlet.class);
//		serve("/worker/identifySpoms").with(SpomIdentificationTaskServlet.class);
	}
}