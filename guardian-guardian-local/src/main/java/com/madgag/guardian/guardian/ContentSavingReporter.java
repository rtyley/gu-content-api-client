package com.madgag.guardian.guardian;

import java.io.File;

import com.google.inject.Inject;
import com.madgag.guardian.guardian.spom.detection.SpomMatch;
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
		String targetId = spomReport.getTargetArticle().getId();
		String groupName = targetId.replace(File.separatorChar, '.');
		contentDumper.dumpContentWithId(groupName, targetId);
		for (SpomMatch spom : spomReport.getSpomsWithMatchScores().values()) {			
			contentDumper.dumpContentWithId(groupName, spom.getSpom().getId());
		}
	}

}
