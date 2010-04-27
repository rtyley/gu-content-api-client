package com.madgag.guardian.guardian;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Collection;

import com.google.inject.Injector;
import com.madgag.appengine.taskqueue.Deferrable;
import com.madgag.guardian.guardian.spom.detection.SpomIdentifier;

public class ArticleSpomSearch implements Deferrable {

	private static final long serialVersionUID = 1L;
	
	private final String preferredMasterId;
	private final Collection<String> listOfPossibleSpomIds;

	public ArticleSpomSearch(String preferredMasterId,Collection<String> listOfPossibleSpomIds ) {
		this.preferredMasterId = preferredMasterId;
		this.listOfPossibleSpomIds = newHashSet(listOfPossibleSpomIds);
	}
	
	@Override
	public void run() {
		Injector injector = MyGuiceServletContextListener.INJECTOR;
		SpomIdentifier spomIdentifer = injector.getInstance(SpomIdentifier.class);
		spomIdentifer.identifySpomsFor(preferredMasterId, listOfPossibleSpomIds);
	}
}