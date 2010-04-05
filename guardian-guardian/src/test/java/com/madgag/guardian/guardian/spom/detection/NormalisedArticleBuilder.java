package com.madgag.guardian.guardian.spom.detection;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.madgag.guardian.contentapi.jaxb.Tag;
import com.madgag.guardian.contentapi.jaxb.TagBuilder;
import org.joda.time.DateTime;

public class NormalisedArticleBuilder {

	private String originalBody;
	private String id="defaultId";
    Multimap<String, Tag> tags = ArrayListMultimap.create();

	public NormalisedArticleBuilder originalBody(String originalBody) {
		this.originalBody = originalBody;
		return this;
	}



	public NormalisedArticleBuilder id(String id) {
		this.id = id;
		return this;
	}

    public NormalisedArticleBuilder contributorIds(String... contributorIds) {
        for (String contributorId : contributorIds) {
            tags.put("contributor", new TagBuilder().type("contributor").id(contributorId).toTag());
        }
        return this;
    }

    public NormalisedArticle toArticle() {
        return new NormalisedArticle(id, "", null, originalBody, new DateTime(), tags );
	}
}
