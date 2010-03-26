package com.madgag.guardian.guardian.spom.detection;

public class DetectedSpom {

	private final NormalisedArticle preferredMaster;
	private final NormalisedArticle bestMatchedSpom;

	public DetectedSpom(NormalisedArticle preferredMaster, NormalisedArticle bestMatchedSpom) {
		this.preferredMaster = preferredMaster;
		this.bestMatchedSpom = bestMatchedSpom;
	}

	public NormalisedArticle getPreferredMasterArticle() {
		return preferredMaster;

	}

	public NormalisedArticle getSpom() {
		return bestMatchedSpom;
	}

	@Override
	public String toString() {
		return this.getClass().toString() + "[" + preferredMaster.getId()
				+ "," + bestMatchedSpom.getId() + "]";
	}

}
