package com.madgag.guardian.contentapi;

import com.madgag.guardian.contentapi.jaxb.Content;
import com.madgag.guardian.contentapi.jaxb.SearchResponse;
import org.junit.Test;


public class ContentApiClientTest {
    @Test
    public void testSearch() throws Exception {
        SearchResponse response = new ContentApiClient().search().withTags("politics/general-election-2010").showFields("short-url").execute();
        for (Content content : response.contents) {
            System.out.println("BOO "+content.getField("short-url"));
        }
    }

}
