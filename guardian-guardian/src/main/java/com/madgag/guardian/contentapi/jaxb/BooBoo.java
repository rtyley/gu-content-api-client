/**
 * 
 */
package com.madgag.guardian.contentapi.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class BooBoo extends XmlAdapter<String, DateTime> {
	private static DateTimeFormatter ISO_FORMAT = ISODateTimeFormat.dateTimeNoMillis();
	
	public BooBoo() {
		System.out.println("moo");
	}

	@Override
	public String marshal(DateTime v) throws Exception {
		return ISO_FORMAT.print(v);
	}

	@Override
	public DateTime unmarshal(String v) throws Exception {
		return ISO_FORMAT.parseDateTime(v);
	}
}