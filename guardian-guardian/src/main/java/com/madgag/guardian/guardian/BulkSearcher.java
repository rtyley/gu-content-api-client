package com.madgag.guardian.guardian;

import org.joda.time.Interval;

import com.google.inject.Inject;
import com.madgag.guardian.guardian.spom.detection.NormalisedArticle;
import com.madgag.guardian.guardian.spom.detection.SpomIdentifier;

public class BulkSearcher {

	private final SpomIdentifier spomIdentifier;
	private final BulkSearchSpaceGenerator bulkSearchSpaceGenerator;

	@Inject
	public BulkSearcher(BulkSearchSpaceGenerator bulkSearchSpaceGenerator, SpomIdentifier spomIdentifier) {
		this.spomIdentifier = spomIdentifier;
		this.bulkSearchSpaceGenerator = bulkSearchSpaceGenerator;
	}

	public void search(Interval interval) {
		SearchSpace searchSpace = bulkSearchSpaceGenerator.getSearchSpaceCovering(interval);
		for (NormalisedArticle preferredMaster : searchSpace.getArticlesToCheck()) {
			spomIdentifier.identifySpomsFor(preferredMaster, searchSpace.possibleSpomIdsFor(preferredMaster));
		}
	}

}
