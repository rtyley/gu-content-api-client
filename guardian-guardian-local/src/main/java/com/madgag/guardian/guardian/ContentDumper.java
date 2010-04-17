package com.madgag.guardian.guardian;

import static java.util.logging.Level.SEVERE;
import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.io.IOUtils.copy;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URI;
import java.util.logging.Logger;

import com.google.inject.Inject;

public class ContentDumper {

	private static final Logger log = Logger.getLogger(ContentDumper.class.getName());

	private final File dumpDirectory;
	private final PopulatedArticleSearchRequestProvider articleSearchRequestProvider;

	@Inject
	public ContentDumper(PopulatedArticleSearchRequestProvider articleSearchRequestProvider, File dumpDirectory) {
		this.articleSearchRequestProvider = articleSearchRequestProvider;
		this.dumpDirectory = dumpDirectory;
	}

	public void dumpContentWithId(String contentId) {
		URI uri = articleSearchRequestProvider.contentWithId(contentId).toUri();
		File dumpFile = new File(dumpDirectory, contentId.replace('/', '.')+".xml");
		try {
			log.info("Dumping " + contentId + " to "+ dumpFile.getAbsolutePath());
			FileWriter writer = new FileWriter(dumpFile);
			try {
				InputStream urlStream = uri.toURL().openStream();
				copy(urlStream, writer);
				closeQuietly(urlStream);
			} finally {
				closeQuietly(writer);
			}
		} catch (Exception e) {
			log.log(SEVERE, "Unable to dump " + uri, e);
		}
	}
}
