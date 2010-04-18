package com.madgag.guardian.guardian;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.google.inject.Guice;
import com.google.inject.Injector;

import static java.lang.System.currentTimeMillis;

public class LocalServer {
	public static void main(String[] args) throws Exception {
		
		Injector injector = Guice.createInjector(new ConfigModule(), new LocalServerConfig());
	    
		Interval searchInterval = new Interval(new DateTime(2010, 2, 23, 0, 0, 0, 0),new DateTime(2010, 2, 25, 0, 0, 0, 0));
	    
	    BulkSearcher bulkSearcher = injector.getInstance(BulkSearcher.class);
	    long start= currentTimeMillis();
	    bulkSearcher.search(searchInterval);
	    long end= currentTimeMillis();
	    System.out.println("Bloh"+(end-start));
	    
	}

}
