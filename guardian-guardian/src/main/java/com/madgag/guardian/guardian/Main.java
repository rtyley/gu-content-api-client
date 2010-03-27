package com.madgag.guardian.guardian;

import static com.google.common.collect.Lists.transform;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.madgag.guardian.contentapi.Hitter;
import com.madgag.guardian.contentapi.SearchRequest;
import com.madgag.guardian.contentapi.jaxb.SearchResponse;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;
import com.madgag.guardian.guardian.spom.detection.SpomCandidateFinder;
import com.madgag.guardian.guardian.spom.detection.SpomIdentifier;

public class Main {
	public static void main(String[] args) throws IOException, JAXBException {
		Injector injector = Guice.createInjector(new ConfigModule());
	    Hitter hitter = injector.getInstance(Hitter.class);
	    
		SearchRequest searchRequest = new SearchRequest();
	    
	    searchRequest.setInterval(new Interval(new DateTime(2009, 9, 17, 0, 0, 0, 0),new DateTime(2009, 9, 23, 0, 0, 0, 0)));
	    searchRequest.setTags("culture/eddie-izzard");
	    searchRequest.showFields("body");
	    
	    SearchResponse response = hitter.jojo(searchRequest);
	    
	    List<NormalisedArticle> nas = transform(response.contents, new ContentNormaliserTransform());
	    
	    SpomCandidateFinder spomCandidateFinder=injector.getInstance(SpomCandidateFinder.class);
	    SpomIdentifier spomIdentifier=injector.getInstance(SpomIdentifier.class);
	    for (NormalisedArticle na : nas) {
	    	Set<String> candidates=spomCandidateFinder.findSpomCandidatesFor(na);
	    	spomIdentifier.identifySpomsFor(na, candidates);
	    }
	    
	    
	    //spomIdentifier.identifySpomsFor(nas.get(0), nas.subList(1, nas.size()));
	    
	}
}
