package com.madgag.guardian.guardian;

import com.google.inject.servlet.ServletModule;

public class MyServletModule extends ServletModule {
	@Override
	protected void configureServlets() {
		serve("/search").with(MyServlet.class);
	}
}