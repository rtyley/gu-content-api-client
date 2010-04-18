package com.madgag.guardian.guardian;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.net.URI;

import org.junit.Test;


public class DiffUrlGeneratorTest {
	
	@Test
	public void shouldCreateValidDiffUrl() throws Exception {
		assertThat(new DiffUrlGenerator().uriFor("film/filmblog/2008/jan/05/ifonlywecouldtopplehollyw", "film/2008/jan/05/2"),
			equalTo(URI.create("http://guplicate.appspot.com/diff?left=film/filmblog/2008/jan/05/ifonlywecouldtopplehollyw&right=film/2008/jan/05/2")));
	}
}
