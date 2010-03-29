package com.madgag.guardian.guardian;

import net.sf.jsr107cache.Cache;

import com.google.inject.AbstractModule;

public class LocalServerConfig extends AbstractModule {

	@Override
	protected void configure() {
		binder().bind(Cache.class).toProvider(FunkyCacheProvider.class).asEagerSingleton();
	}

}
