package com.madgag.guardian.guardian;

import com.google.inject.Inject;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;
import com.madgag.guardian.guardian.spom.detection.SpomReport;
import com.madgag.guardian.guardian.spom.detection.reporting.SpomDetectionReporter;

public class ContentSavingReporter implements SpomDetectionReporter {

	private final ContentDumper contentDumper;
	
	@Inject
	public ContentSavingReporter(ContentDumper contentDumper) {
		this.contentDumper = contentDumper;
	}
	
	@Override
	public void report(SpomReport spomReport) {
		if (spomReport.hasDetectedSpoms()) {			
			dumpArticlesFrom(spomReport);
		}
	}

	private void dumpArticlesFrom(SpomReport spomReport) {
		contentDumper.dumpContentWithId(spomReport.getTargetArticle().getId());
		for (NormalisedArticle spom : spomReport.getSpomsWithMatchScores().keySet()) {			
			contentDumper.dumpContentWithId(spom.getId());
		}
	}

}
