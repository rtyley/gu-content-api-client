package com.madgag.guardian.guardian.spom.detection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.madgag.text.util.LevenshteinWithDistanceThreshold;

@RunWith(MockitoJUnitRunner.class)
public class ValidArticleFilterTest {

	private ValidArticleFilter filter = new ValidArticleFilter(new LevenshteinWithDistanceThreshold());
	
	@Test
	public void shouldReturnFalseForAnExpiredArticle() {
		NormalisedArticle na=new NormalisedArticleBuilder()
			.originalBody("<p>This article has been removed as our copyright has expired.</p><!-- Guardian Watermark: books/2010/jan/17/mark-kermode-only-movie-extract|2010-03-31T00:00:17+01:00|fdb92f7a70b8a0c04cfae02a0f3f457ca5a41ed6 -->")
			.toArticle();
		
		assertThat(filter.apply(na),is(false));
	}
	
	@Test
	public void shouldReturnTrueForSomeSimliarButGoodText() {
		NormalisedArticle na=new NormalisedArticleBuilder()
			.originalBody("<p>This article might be removed as our copyright laws are tired.</p><!-- Guardian Watermark: books/2010/jan/17/mark-kermode-only-movie-extract|2010-03-31T00:00:17+01:00|fdb92f7a70b8a0c04cfae02a0f3f457ca5a41ed6 -->")
			.toArticle();
		
		assertThat(filter.apply(na),is(true));
	}
}
