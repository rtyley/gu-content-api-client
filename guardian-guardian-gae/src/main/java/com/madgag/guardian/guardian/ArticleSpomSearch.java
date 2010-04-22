package com.madgag.guardian.guardian;

import static com.google.common.collect.Sets.newHashSet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;

import com.google.inject.Injector;
import com.madgag.guardian.guardian.spom.detection.SpomIdentifier;
import com.newatlanta.appengine.taskqueue.Deferred.Deferrable;

public class ArticleSpomSearch implements Deferrable {

	private static final long serialVersionUID = 1L;
	
	private final String preferredMasterId;
	private final Collection<String> listOfPossibleSpomIds;

	public ArticleSpomSearch(String preferredMasterId,Collection<String> listOfPossibleSpomIds ) {
		this.preferredMasterId = preferredMasterId;
		this.listOfPossibleSpomIds = newHashSet(listOfPossibleSpomIds);
	}
	
	@Override
	public void doTask() throws ServletException, IOException {
		Injector injector = MyGuiceServletContextListener.INJECTOR;
		SpomIdentifier spomIdentifer = injector.getInstance(SpomIdentifier.class);
		spomIdentifer.identifySpomsFor(preferredMasterId, listOfPossibleSpomIds);
	}
}