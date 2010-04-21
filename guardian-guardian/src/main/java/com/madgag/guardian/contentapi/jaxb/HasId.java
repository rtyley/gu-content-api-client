package com.madgag.guardian.contentapi.jaxb;

import com.google.common.base.Function;


public interface HasId {

	String getId();

	public static Function<HasId, String> GET_ID = new Function<HasId, String>() {
		public String apply(HasId idOwner) { return idOwner.getId(); }
	};
	
}
