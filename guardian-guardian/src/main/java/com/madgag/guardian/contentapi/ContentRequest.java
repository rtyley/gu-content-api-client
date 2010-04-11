package com.madgag.guardian.contentapi;

public interface ContentRequest<Req extends ContentRequest<Req>> {

	public Req showFields(String... fields);

	public Req showTags(String... tagTypes);
}
