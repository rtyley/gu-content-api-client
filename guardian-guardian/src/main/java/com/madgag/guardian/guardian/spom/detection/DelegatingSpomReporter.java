package com.madgag.guardian.guardian.spom.detection;

import java.util.Set;

import com.google.inject.Inject;

public class DelegatingSpomReporter implements SpomDetectionReporter {
	
	private final Set<SpomDetectionReporter> spomDetectionReporters;

	@Inject
	public DelegatingSpomReporter(Set<SpomDetectionReporter> spomDetectionReporters) {
		this.spomDetectionReporters = spomDetectionReporters;
	}
	
	@Override
	public void reportStuff(NormalisedArticle preferredMaster, NormalisedArticle possibleSpom, float currentMatchScore) {
		for (SpomDetectionReporter spomDetectionReporter : spomDetectionReporters) {
			spomDetectionReporter.reportStuff(preferredMaster, possibleSpom, currentMatchScore);
		}
	}

}
