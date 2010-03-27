package com.madgag.guardian.guardian.spom.detection;

import static com.google.common.collect.Sets.newHashSetWithExpectedSize;

import java.io.IOException;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.google.inject.Inject;
import com.madgag.guardian.contentapi.Hitter;
import com.madgag.guardian.contentapi.SearchRequest;
import com.madgag.guardian.contentapi.jaxb.Content;
import com.madgag.guardian.contentapi.jaxb.SearchResponse;

public class SpomCandidateFinder {
	
	private final Hitter hitter;

	@Inject
	public SpomCandidateFinder(Hitter hitter) {
		this.hitter = hitter;
	}
	
	public Set<String> findSpomCandidatesFor(NormalisedArticle preferredArticle) throws IOException, JAXBException {
		SearchRequest searchRequest=new SearchRequest();
		DateTime dateTime = preferredArticle.getWebPublicationDate();
		
		searchRequest.setInterval(new Interval(dateTime.minusDays(1),dateTime.plusDays(1)));
		SearchResponse boo = hitter.jojo(searchRequest);
		Set<String> spomCandidateSet = newHashSetWithExpectedSize(boo.contents.size());
		for (Content content : boo.contents) {
			spomCandidateSet.add(content.id);
		}
		spomCandidateSet.remove(preferredArticle.getId());
		
		return spomCandidateSet;
	}
}
