package com.madgag.guardian.guardian.spom.detection;
import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;


public class NormalisedArticleTest {

	@Test
	public void shouldCreateNormalisedArticleWithNormalisedBodyText() {
		NormalisedArticle article = new NormalisedArticle("myId", "<someTag>A \t\t  Body  Text?... &  1 2 3  </someclosingTag><!-- Guardian Watermark: culture/2009/sep/20/eddie-izzard-marathon-runner|2010-03-26T22:29:09Z|5662130e2da55b1d82328d689617daaae9b08371 -->",null, newHashSet("oo"));
		
		assertThat(article.getNormalisedBodyText(), equalTo("a body text 1 2 3"));
	}
}
