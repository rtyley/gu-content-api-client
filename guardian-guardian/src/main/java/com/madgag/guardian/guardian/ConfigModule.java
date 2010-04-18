package com.madgag.guardian.guardian;

import static com.rosaloves.net.shorturl.bitly.BitlyFactory.newJmpInstance;

import java.io.InputStream;
import java.util.Properties;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.madgag.guardian.guardian.spom.detection.reporting.DelegatingSpomReporter;
import com.madgag.guardian.guardian.spom.detection.reporting.SpomDetectionReporter;
import com.rosaloves.net.shorturl.bitly.Bitly;

public class ConfigModule extends AbstractModule {

	@Override
	protected void configure() {
		loadProperties(binder());
		binder().bind(SpomDetectionReporter.class).to(DelegatingSpomReporter.class);
		binder().bind(NormalisedArticleProvider.class).to(CachingNormalisedArticleProvider.class);
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
	protected Bitly getBitlyClient(@Named("bitly-api.username") String username, @Named("bitly-api.key") String apiKey) {
		return newJmpInstance(username, apiKey);
	}
	
	@Provides
	protected Twitter getTwitterClient(@Named("twitter.username") String username, @Named("twitter.password") String password) {
		return new TwitterFactory().getInstance(username, password);
	}

}
