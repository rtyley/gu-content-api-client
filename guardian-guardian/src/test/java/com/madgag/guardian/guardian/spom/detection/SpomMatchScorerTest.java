package com.madgag.guardian.guardian.spom.detection;
import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.madgag.text.util.LevenshteinWithDistanceThreshold;

@RunWith(MockitoJUnitRunner.class)
public class SpomMatchScorerTest {
	@Mock LevenshteinWithDistanceThreshold levenshteinWithDistanceThreshold;
	
	private SpomMatchScorer spomMatchScorer;

	@Before
	public void setUp() {
		spomMatchScorer = new SpomMatchScorer(levenshteinWithDistanceThreshold);
	}
	
	@Test
	public void shouldReturnBetterMatchScoreForArticlesWhichHaveMatchingContributors() throws Exception {
		String tedContributor = "ted", bobContributor = "bob";
		NormalisedArticle tedArticle = normalisedArticleWithText("1", tedContributor);
		NormalisedArticle possibleSpomByTed = normalisedArticleWithText("1", tedContributor);
		NormalisedArticle possibleSpomByBob = normalisedArticleWithText("1", bobContributor);
		when(levenshteinWithDistanceThreshold.get(anyString(), anyString(), anyInt())).thenReturn(1);
		
		float scoreForArticleWithMatchingContributor = spomMatchScorer.getMatchScore(tedArticle, possibleSpomByTed, 1f);
		float scoreForArticleWithDifferentContributor = spomMatchScorer.getMatchScore(tedArticle, possibleSpomByBob, 1f);
		
		assertThat(scoreForArticleWithMatchingContributor, not(equalTo(Float.MAX_VALUE)));
		assertThat(scoreForArticleWithDifferentContributor, equalTo(scoreForArticleWithMatchingContributor + SpomMatchScorer.CONTRIBUTOR_DOES_NOT_MATCH_WEIGHTING));
	}
	
	@Test
	public void shouldReturnFloatMaxValueIfLevenshteinDistanceIsOverThreshold() throws Exception {
		Mockito.when(levenshteinWithDistanceThreshold.get(anyString(), anyString(), anyInt())).thenReturn(Integer.MAX_VALUE);
		
		assertThat(spomMatchScorer.getMatchScore(normalisedArticleWithText("1"), normalisedArticleWithText("2"), 0.05f), equalTo(Float.MAX_VALUE));
	}
	
	@Test
	public void shouldScoreGoodMatchAsBetterThanWorseMatch() throws Exception {
		
		spomMatchScorer = new SpomMatchScorer(new LevenshteinWithDistanceThreshold());
		
		NormalisedArticle preferredMaster = normalisedArticleWithText("I like fish");
		NormalisedArticle goodMatch = normalisedArticleWithText("I love fish");
		NormalisedArticle badMatch = normalisedArticleWithText("Monkey");
		
		float scoreForGoodMatch = spomMatchScorer.getMatchScore(preferredMaster, goodMatch, 1);
		float scoreForBadMatch = spomMatchScorer.getMatchScore(preferredMaster, badMatch, 1);
		
		assertThat(scoreForGoodMatch, lessThan(scoreForBadMatch));
	}

	private NormalisedArticle normalisedArticleWithText(String bodyText, String... contributor) {
		return new NormalisedArticle("myId",bodyText, newHashSet(contributor));
	}
}
