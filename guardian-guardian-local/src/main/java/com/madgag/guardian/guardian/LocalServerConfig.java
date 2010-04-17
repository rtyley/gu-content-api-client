package com.madgag.guardian.guardian;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

import java.io.File;

import net.sf.jsr107cache.Cache;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import com.madgag.guardian.guardian.spom.detection.reporting.SpomDetectionReporter;
import com.madgag.guardian.guardian.spom.detection.reporting.TwitterReporter;

public class LocalServerConfig extends AbstractModule {

	@Override
	protected void configure() {
		binder().bind(Cache.class).toProvider(FunkyCacheProvider.class).asEagerSingleton();
		Multibinder<SpomDetectionReporter> reporterBinder = newSetBinder(binder(), SpomDetectionReporter.class);
		reporterBinder.addBinding().to(ContentSavingReporter.class);
		//reporterBinder.addBinding().to(TwitterReporter.class);
	}
	
	@Provides
	public File provideDumpDir() {
		return new File(System.getProperty("java.io.tmpdir"));
	}

}
