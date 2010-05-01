package com.madgag.guardian.contentapi;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class ApiConfig {

	private final String apiKey;
	private final String serverPath;

	public ApiConfig(String serverPath) {
		this(serverPath, null);
	}

	@Inject
	public ApiConfig(
			@Named("guardian-content-api.server-path") String serverPath,
			@Named("guardian-content-api.key") String apiKey) {
		this.apiKey = apiKey;
		this.serverPath = serverPath;
	}

	public String getApiKey() {
		return apiKey;
	}

	public String getServerPath() {
		return serverPath;
	}
}
