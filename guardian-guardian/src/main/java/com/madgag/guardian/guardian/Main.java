package com.madgag.guardian.guardian;

import static com.google.common.collect.Lists.transform;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheManager;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.madgag.guardian.contentapi.ContentApiClient;
import com.madgag.guardian.contentapi.jaxb.SearchResponse;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;
import com.madgag.guardian.guardian.spom.detection.SpomCandidateFinder;
import com.madgag.guardian.guardian.spom.detection.SpomIdentifier;

public class Main {
	public static void main(String[] args) throws Exception {
		
		
		Cache cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
		
		
		Injector injector = Guice.createInjector(new ConfigModule());
	    
	    Interval searchInterval = new Interval(new DateTime(2009, 9, 17, 0, 0, 0, 0),new DateTime(2009, 9, 23, 0, 0, 0, 0));
	    ContentApiClient apiClient=injector.getInstance(ContentApiClient.class);
	    
	    SearchResponse searchResponse=
	    	apiClient.search().during(searchInterval).withTags("culture/eddie-izzard").showFields("body").execute();
	    
	    List<NormalisedArticle> nas = transform(searchResponse.contents, new ContentNormaliserTransform());
	    
	    SpomCandidateFinder spomCandidateFinder=injector.getInstance(SpomCandidateFinder.class);
	    SpomIdentifier spomIdentifier=injector.getInstance(SpomIdentifier.class);
	    for (NormalisedArticle na : nas) {
	    	Set<String> candidates=spomCandidateFinder.findSpomCandidatesFor(na);
	    	spomIdentifier.identifySpomsFor(na, candidates);
	    }
	}
}
