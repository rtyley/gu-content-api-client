package com.madgag.guardian.guardian;

import java.io.InputStream;
import java.util.Properties;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class ConfigModule extends AbstractModule {

	@Override
	protected void configure() {
		loadProperties(binder());
		binder().bind(NormalisedArticleProvider.class).to(CachingNormalisedArticleProvider.class);
		//binder().bind(NormalisedArticleProvider.class).to(ContentApiNormalisedArticleProvider.class);
	}

	private void loadProperties(Binder binder) {
		InputStream stream = ConfigModule.class.getResourceAsStream("/config.properties");
		Properties appProperties = new Properties();
		try {
			appProperties.load(stream);
			Names.bindProperties(binder, appProperties);
		} catch (Exception e) {
			// This is the preferred way to tell Guice something went wrong
			binder.addError(e);
		}
	}
	
	@Provides
	protected Twitter getTwitterClient(@Named("twitter.username") String username, @Named("twitter.password") String password) {
		return new TwitterFactory().getInstance(username, password);
	}

}
