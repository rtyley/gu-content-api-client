package com.madgag.spom.detection;
import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;


public class NormalisedArticleTest {

	@Test
	public void shouldCreateNormalisedArticleWithNormalisedBodyText() {
		NormalisedArticle article = new NormalisedArticle("myId", "<someTag>A \t\t  Body  Text?... &  1 2 3  </someclosingTag>",newHashSet("oo"));
		
		assertThat(article.getNormalisedBodyText(), equalTo("a body text 1 2 3"));
		
	}
}
