package com.madgag.guardian.guardian.spom.detection;

import static com.google.common.collect.Sets.newHashSetWithExpectedSize;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import org.joda.time.DateTime;

import com.google.inject.Inject;
import com.madgag.guardian.contentapi.SearchRequest;
import com.madgag.guardian.contentapi.jaxb.Content;
import com.madgag.guardian.contentapi.jaxb.SearchResponse;
import com.madgag.guardian.guardian.CachingNormalisedArticleProvider;
import com.madgag.guardian.guardian.ContentNormaliserTransform;
import com.madgag.guardian.guardian.PopulatedArticleSearchRequestProvider;

public class SpomCandidateFinder {
	
	private static final Logger log = Logger.getLogger(SpomCandidateFinder.class.getName());

	private final SearchRequest searchRequestForArticlesWithPopulatedFields;

	private final CachingNormalisedArticleProvider cachingNormalisedArticleProvider; // this is badness, right?

	@Inject
	public SpomCandidateFinder(PopulatedArticleSearchRequestProvider articleSearchRequestProvider, CachingNormalisedArticleProvider cachingNormalisedArticleProvider) {
		searchRequestForArticlesWithPopulatedFields = articleSearchRequestProvider.articleSearch();
		this.cachingNormalisedArticleProvider = cachingNormalisedArticleProvider;
	}
	
	public Set<String> findSpomCandidatesFor(NormalisedArticle preferredArticle) throws IOException, JAXBException {
		DateTime dateTime = preferredArticle.getWebPublicationDate();

		SearchResponse boo = searchRequestForArticlesWithPopulatedFields
			.from(dateTime.minusDays(1)).to(dateTime.plusDays(1))
			.pageSize(50)
			.execute();
		Set<String> spomCandidateSet = newHashSetWithExpectedSize(boo.total);
		while (boo!=null) {
			for (Content content : boo.contents) {
				spomCandidateSet.add(content.id);
				cachingNormalisedArticleProvider.store(new ContentNormaliserTransform().apply(content));
			}
			boo=boo.next();
		}
		spomCandidateSet.remove(preferredArticle.getId());
		log.info("Found "+spomCandidateSet.size()+" candidates for "+preferredArticle.getId());
		return spomCandidateSet;
	}
}
