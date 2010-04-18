package com.madgag.guardian.guardian.spom.detection;

import static com.google.common.collect.Maps.uniqueIndex;

import java.util.List;
import java.util.Map;

import com.google.common.base.Function;

public class SpomReport {
	
	private NormalisedArticle targetArticle;
	private Map<String,SpomMatch> detectedSpoms;
	
	public SpomReport(NormalisedArticle targetArticle, List<SpomMatch> spomMatches) {
		this.targetArticle = targetArticle;
		this.detectedSpoms = uniqueIndex(spomMatches, new Function<SpomMatch, String>() {
			public String apply(SpomMatch match) { return match.getSpom().getId(); }
		});
	}

	public NormalisedArticle getTargetArticle() {
		return targetArticle;
	}
	
	public Map<String,SpomMatch> getSpomsWithMatchScores() {
		return detectedSpoms;
	}

	public boolean hasDetectedSpoms() {
		return !detectedSpoms.isEmpty();
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+"["+targetArticle+" - "+detectedSpoms+"]";
	}

}
