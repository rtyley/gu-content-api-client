package com.madgag.guardian.contentapi;

import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.inject.Inject;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.SEVERE;

public class JavaNetUrlHitter implements UrlHitter {

	private static final Logger log = Logger.getLogger(JavaNetUrlHitter.class.getName());

	private final ApiConfig apiConfig;

	@Inject
	public JavaNetUrlHitter(ApiConfig apiConfig) {
		this.apiConfig = apiConfig;
	}

	@SuppressWarnings("unchecked")
	public <Req extends ApiRequest<Req, Resp>, Resp extends ApiResponse<Req, Resp>> Resp makeWebRequestFor(Req apiRequest) {
		URI uri = apiRequest.toUri();
		try {
			URL url = uri.toURL();
            if (log.isLoggable(FINE)) {
			    log.fine(url.toString());
            }
			Resp response = (Resp) apiRequest.getJaxbContextForResponse().createUnmarshaller().unmarshal(url.openStream());
			response.setOriginalRequest(apiRequest);
			return response;
        } catch (java.net.MalformedURLException mue) {
            log.log(SEVERE,"Apparently this is a malformed URL: ---"+uri+"---",mue);
            throw new ContentApiException("Bizarre Malformed URL Exception reading from "+uri,mue);
		} catch (Exception e) {
			throw new ContentApiException("Error reading from "+uri,e);
		}
	}

	public ApiConfig getConfig() {
		return apiConfig;
	}

}
