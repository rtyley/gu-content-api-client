package com.madgag.guardian.guardian;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.jcache.JCacheManager;
import net.sf.jsr107cache.Cache;

import com.google.inject.Provider;

public class FunkyCacheProvider implements Provider<Cache> {

	@Override
	public Cache get() {
		//Cache cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
		net.sf.ehcache.config.Configuration configuration= new Configuration();
		CacheConfiguration cacheConfiguration = new CacheConfiguration();
		cacheConfiguration.setName("sampleCacheNoIdle");
		cacheConfiguration.setMaxElementsInMemory(1000000);
		configuration.addCache(cacheConfiguration);
		
		CacheConfiguration defCache = new CacheConfiguration();
		configuration.setDefaultCacheConfiguration(defCache);
		JCacheManager ehCacheManager = new JCacheManager(configuration);
		
		return ehCacheManager.getJCache("sampleCacheNoIdle");
	}

}
