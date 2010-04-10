package com.madgag.guardian.guardian;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

import java.util.Collections;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
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

	@Singleton
	@Provides
	public Cache provideCache() throws CacheException {
		return CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
	}
}
