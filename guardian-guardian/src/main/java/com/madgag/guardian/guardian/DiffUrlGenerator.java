package com.madgag.guardian.guardian;

import java.net.URI;

public class DiffUrlGenerator {

	public URI uriFor(String leftId, String rightId) {
		return URI.create("http://guplicate.appspot.com/diff?left="+leftId+"&right="+rightId);
	}
	
}
