/**
 * 
 */
package com.madgag.guardian.guardian;

import static com.google.common.collect.Sets.newHashSet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;

import com.madgag.guardian.guardian.spom.detection.SpomIdentifier;
import com.newatlanta.appengine.taskqueue.Deferred.Deferrable;

public class ArticleSpomSearch implements Deferrable {
	private String preferredMasterId;
	private Collection<String> listOfPossibleSpomIds;

	public ArticleSpomSearch(String preferredMasterId,Collection<String> listOfPossibleSpomIds ) {
		this.preferredMasterId = preferredMasterId;
		this.listOfPossibleSpomIds = newHashSet(listOfPossibleSpomIds);
	}
	
	@Override
	public void doTask() throws ServletException, IOException {
		SpomIdentifier spomIdentifer = MyGuiceServletContextListener.INJECTOR.getInstance(SpomIdentifier.class);
		spomIdentifer.identifySpomsFor(preferredMasterId, listOfPossibleSpomIds);
	}
}