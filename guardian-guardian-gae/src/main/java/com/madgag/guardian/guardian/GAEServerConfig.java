package com.madgag.guardian.guardian;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.madgag.guardian.guardian.spom.detection.reporting.SpomDetectionReporter;
import com.madgag.guardian.guardian.spom.detection.reporting.TwitterReporter;

public class GAEServerConfig extends AbstractModule {

	@Override
	protected void configure() {
		//binder().bind(Cache.class).toProvider(FunkyCacheProvider.class).asEagerSingleton();
		Multibinder<SpomDetectionReporter> reporterBinder = newSetBinder(binder(), SpomDetectionReporter.class);
		reporterBinder.addBinding().to(TwitterReporter.class);
	}

}
