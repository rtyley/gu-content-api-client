package com.madgag.guardian.contentapi.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class JAXBUtil {
	public static JAXBContext createJAXBContextFor(Class... classesToBeBound) {
		try {
			return JAXBContext.newInstance(classesToBeBound);
		} catch (JAXBException e) {
			throw new RuntimeException();
		}
	}
	
}
