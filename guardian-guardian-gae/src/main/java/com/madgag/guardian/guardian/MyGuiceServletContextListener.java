package com.madgag.guardian.guardian;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class MyGuiceServletContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(
				new ConfigModule(),
				new MyServletModule(),
				new GAEServerConfig());
	}

}
