package com.madgag.guardian.contentapi.jaxb;

import java.net.URI;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class StringUriAdapter extends XmlAdapter<String, URI> {
	@Override
	public String marshal(URI v) throws Exception {
		return v.toString();
	}

	@Override
	public URI unmarshal(String v) throws Exception {
		return URI.create(v);
	}

}
