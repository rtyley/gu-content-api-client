package com.madgag.guardian.guardian.spom.detection.reporting;

import java.util.Set;

import com.google.inject.Inject;
import com.madgag.guardian.guardian.spom.detection.SpomReport;

public class DelegatingSpomReporter implements SpomDetectionReporter {
	
	private final Set<SpomDetectionReporter> spomDetectionReporters;

	@Inject
	public DelegatingSpomReporter(Set<SpomDetectionReporter> spomDetectionReporters) {
		this.spomDetectionReporters = spomDetectionReporters;
	}
	
	@Override
	public void report(SpomReport spomReport) {
		for (SpomDetectionReporter spomDetectionReporter : spomDetectionReporters) {
			spomDetectionReporter.report(spomReport);
		}
	}

}
