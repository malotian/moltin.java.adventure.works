/**
 */
package com.moltin.api;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.logging.LoggingFeature.Verbosity;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RestRequest {

	static final JerseyClient client;
	static final JsonParser parser = new JsonParser();

	static {
		java.util.logging.LogManager.getLogManager().reset();
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		final ClientConfig config = new ClientConfig();
		config.register(JacksonFeature.class);
		config.register(JacksonJsonProvider.class);
		config.register(jersyLoggingFeature());
		client = JerseyClientBuilder.createClient(config);
	}

	public static JerseyClient client() {
		return client;
	}

	public static LoggingFeature jersyLoggingFeature() {
		final java.util.logging.Logger jerseyLogger = java.util.logging.Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME);
		jerseyLogger.setLevel(java.util.logging.Level.ALL);
		return new LoggingFeature(jerseyLogger, Verbosity.PAYLOAD_TEXT);
	}

	public static JsonObject toJsonObject(String input) {
		return parser.parse(input).getAsJsonObject();
	}

	protected RestRequest() {
	}
}
