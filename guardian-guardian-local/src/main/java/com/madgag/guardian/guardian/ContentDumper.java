package com.madgag.guardian.guardian;

import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.google.inject.Inject;
import com.madgag.guardian.contentapi.ContentApiClient;

public class ContentDumper {
	
	private static final Logger log = Logger.getLogger(ContentDumper.class.getName());
	
	private final ContentApiClient apiClient;
	private final File dumpDirectory;

	@Inject
	public ContentDumper(ContentApiClient apiClient, File dumpDirectory) {
		this.apiClient = apiClient;
		this.dumpDirectory = dumpDirectory;
	}
	
	public void dumpContentWithId(String contentId) {
		URI uri = apiClient.loadPageWith(contentId).toUri();
		File dumpFile = new File(dumpDirectory,contentId.replace('/', '.'));
		try {
			log.info("Dumping "+contentId+" to "+dumpFile.getAbsolutePath());
			IOUtils.copy(uri.toURL().openStream(), new FileWriter(dumpFile));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
