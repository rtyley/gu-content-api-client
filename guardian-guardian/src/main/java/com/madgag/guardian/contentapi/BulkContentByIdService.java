package com.madgag.guardian.contentapi;

import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.partition;

import java.util.List;
import java.util.Set;

import com.google.common.base.Function;
import com.madgag.guardian.contentapi.jaxb.Content;

public class BulkContentByIdService {	
	
	public static <In,Out> Iterable<Out> batchProcess(List<In> inputItems, int chunkSize, Function<List<In>, List<Out>> func) {
		return concat(transform(partition(inputItems, chunkSize), func));
	}
}
