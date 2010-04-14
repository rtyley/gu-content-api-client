package com.madgag.guardian.guardian.spom.detection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.madgag.guardian.guardian.NormalisedArticleProvider;
import com.madgag.text.util.LevenshteinWithDistanceThreshold;

@RunWith(MockitoJUnitRunner.class)
public class SpomMatchScorerTest {
	@Mock LevenshteinWithDistanceThreshold levenshteinWithDistanceThreshold;
	
	private SpomMatchScorer spomMatchScorer;

	@Before
	public void setUp() {
		spomMatchScorer = new SpomMatchScorer(levenshteinWithDistanceThreshold);
	}
	
//	@Test
//	public void shouldReturnBetterMatchScoreForArticlesWhichHaveMatchingContributors() throws Exception {
//		String tedContributor = "ted", bobContributor = "bob";
//		NormalisedArticle tedArticle = normalisedArticleWithText("1", tedContributor);
//		NormalisedArticle possibleSpomByTed = normalisedArticleWithText("1", tedContributor);
//		NormalisedArticle possibleSpomByBob = normalisedArticleWithText("1", bobContributor);
//		when(levenshteinWithDistanceThreshold.get(anyString(), anyString(), anyInt())).thenReturn(1);
//		
//		float scoreForArticleWithMatchingContributor = spomMatchScorer.getMatchScore(tedArticle, possibleSpomByTed, 1f);
//		float scoreForArticleWithDifferentContributor = spomMatchScorer.getMatchScore(tedArticle, possibleSpomByBob, 1f);
//		
//		assertThat(scoreForArticleWithMatchingContributor, not(equalTo(Float.MAX_VALUE)));
//		assertThat(scoreForArticleWithDifferentContributor, equalTo(scoreForArticleWithMatchingContributor + SpomMatchScorer.CONTRIBUTOR_DOES_NOT_MATCH_WEIGHTING));
//	}
//	
//	@Test
//	public void shouldReturnFloatMaxValueIfLevenshteinDistanceIsOverThreshold() throws Exception {
//		Mockito.when(levenshteinWithDistanceThreshold.get(anyString(), anyString(), anyInt())).thenReturn(Integer.MAX_VALUE);
//		
//		assertThat(spomMatchScorer.getMatchScore(normalisedArticleWithText("1"), normalisedArticleWithText("2"), 0.05f), equalTo(Float.MAX_VALUE));
//	}
//	
//	@Test
//	public void shouldScoreGoodMatchAsBetterThanWorseMatch() throws Exception {
//		
//		spomMatchScorer = new SpomMatchScorer(new LevenshteinWithDistanceThreshold());
//		
//		NormalisedArticle preferredMaster = normalisedArticleWithText("I like fish");
//		NormalisedArticle goodMatch = normalisedArticleWithText("I love fish");
//		NormalisedArticle badMatch = normalisedArticleWithText("Monkey");
//		
//		float scoreForGoodMatch = spomMatchScorer.getMatchScore(preferredMaster, goodMatch, 1);
//		float scoreForBadMatch = spomMatchScorer.getMatchScore(preferredMaster, badMatch, 1);
//		
//		assertThat(scoreForGoodMatch, lessThan(scoreForBadMatch));
//	}
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void shouldBeMoreLenientToBigArticles() throws Exception {
//		assertThat(spomMatchScorer.getThresholdFor(articleWithBodyLength(8))*8f,lessThan(1f));
//		assertThat(spomMatchScorer.getThresholdFor(articleWithBodyLength(30))*30f,allOf(greaterThanOrEqualTo(1f),lessThan(2f)));
//		assertThat(spomMatchScorer.getThresholdFor(articleWithBodyLength(100))*100f,allOf(greaterThanOrEqualTo(9f),lessThan(12f)));
//		assertThat((double) spomMatchScorer.getThresholdFor(articleWithBodyLength(2000)),closeTo(0.25f, 0.01f));
//	}
//	
//	private NormalisedArticle articleWithBodyLength(int length) {
//		return normalisedArticleWithText(textOfLength(length));
//	}
//	
//	private String textOfLength(int len) {
//		StringBuilder sb = new StringBuilder(len);
//		for (int i=0;i<len;++i) {
//			sb.append("a");
//		}
//		return sb.toString();
//	}
//	
//	private NormalisedArticle normalisedArticleWithText(String bodyText, String... contributor) {
//		return new NormalisedArticle("myId",null, null, bodyText, null, null);
//	}
	
	@Test
	public void shouldBeTheCoolest() {
		NormalisedArticleProvider articleProvider=null;
		//NormalisedArticle pm=articleProvider.normalisedArticleFor("books/2010/jan/17/mark-kermode-only-movie-extract");
		//NormalisedArticle ps=articleProvider.normalisedArticleFor("books/2010/jan/17/mark-kermode-only-movie-extract");
		
	}

	@Test
	public void testCommonTags() {
	
	}
}
