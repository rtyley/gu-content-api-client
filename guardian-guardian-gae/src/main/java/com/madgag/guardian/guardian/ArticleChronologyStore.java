package com.madgag.guardian.guardian;

import static com.google.appengine.api.datastore.KeyFactory.createKey;
import static com.google.appengine.api.datastore.Query.FilterOperator.GREATER_THAN_OR_EQUAL;
import static com.google.appengine.api.datastore.Query.FilterOperator.LESS_THAN;
import static com.google.common.collect.Iterables.transform;

import org.joda.time.Interval;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.common.base.Function;
import com.google.inject.Inject;
import com.madgag.guardian.contentapi.jaxb.Content;

public class ArticleChronologyStore {

	private final static String WEB_PUB_DATE = "web-publication-date";
	private final DatastoreService datastoreService;
	
	@Inject
	public ArticleChronologyStore(DatastoreService datastoreService) {
		this.datastoreService = datastoreService;
	}
	
	public Iterable<String> getArticleIdsFor(Interval interval) {
		Query query = new Query("Content").setKeysOnly()
			.addFilter(WEB_PUB_DATE, GREATER_THAN_OR_EQUAL, interval.getStart().getMillis())
			.addFilter(WEB_PUB_DATE, LESS_THAN, interval.getEnd().getMillis());
		return transform(datastoreService.prepare(query).asIterable(),
			new Function<Entity,String>() {
				public String apply(Entity e) { return e.getKey().getName(); }
		});
	}
	
	public void storeChronologyFrom(Iterable<Content> content) {
		Iterable<Entity> entities = transform(content, new Function<Content, Entity>() {
			public Entity apply(Content c) {
				Entity entity = new Entity(createKey("Content", c.getId()));
				entity.setProperty(WEB_PUB_DATE, c.webPublicationDate.getMillis());
				return entity;
			}
		});
		datastoreService.put(entities);
	}
}
