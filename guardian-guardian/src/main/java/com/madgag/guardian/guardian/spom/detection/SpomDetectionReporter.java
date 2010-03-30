package com.madgag.guardian.guardian.spom.detection;

public interface SpomDetectionReporter {

	void reportStuff(NormalisedArticle preferredMaster,	NormalisedArticle possibleSpom, float currentMatchScore);

}
