package com.madgag.guardian.guardian;

import com.google.inject.Inject;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;
import com.madgag.guardian.guardian.spom.detection.SpomDetectionReporter;

public class ContentSavingReporter implements SpomDetectionReporter {

	private final ContentDumper contentDumper;
	
	@Inject
	public ContentSavingReporter(ContentDumper contentDumper) {
		this.contentDumper = contentDumper;
	}
	
	@Override
	public void reportStuff(NormalisedArticle preferredMaster, NormalisedArticle possibleSpom, float currentMatchScore) {
		contentDumper.dumpContentWithId(preferredMaster.getId());
		contentDumper.dumpContentWithId(possibleSpom.getId());
	}

}
