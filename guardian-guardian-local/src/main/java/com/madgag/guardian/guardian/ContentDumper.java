package com.madgag.guardian.guardian;

import static java.util.logging.Level.SEVERE;
import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.io.IOUtils.copy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

	public void dumpContentWithId(String groupName, String contentId) {
		URI uri = articleSearchRequestProvider.contentWithId(contentId).toUri();
		File groupDirectory = new File(dumpDirectory,groupName);
		groupDirectory.mkdirs();
		File dumpFile = new File(groupDirectory, contentId.replace('/', '.')+".xml");
		try {
			logUrl(uri, groupDirectory);

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

	private void logUrl(URI uri, File groupDirectory) throws IOException {
		BufferedWriter output = new BufferedWriter(new FileWriter(new File(groupDirectory,"urls.txt"),true));
		output.append(uri.toString()+"\n");
		output.close();
	}
}
