package com.microsoft.azure.search.api;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.glassfish.jersey.client.JerseyInvocation.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.jcabi.aspects.Loggable;
import com.moltin.adventure.works.app.Configuration;
import com.moltin.adventure.works.app.Context;
import com.rest.RestRequest;

public class AzureSearchRequest extends RestRequest {

	static final Logger LOGGER = LoggerFactory.getLogger(AzureSearchRequest.class);
	String url = StringUtils.EMPTY;

	private String searchAppName = StringUtils.EMPTY;
	private String apiKey = StringUtils.EMPTY;

	public AzureSearchRequest(String... urlPath) {
		final Configuration configuration = Context.get(Configuration.class);
		setSearchAppName(configuration.getAzureSearchAppName());
		setApiKey(configuration.getAzureSearchApiKey());

		url = "https://" + getSearchAppName() + ".search.windows.net/" + StringUtils.join(urlPath, '/') + "?api-version=2015-02-28";
	}

	Builder azure() {
		return client().target(url).request() // $NON-NLS-1$
				.header("api-key", getApiKey());
	}

	public JsonObject create(Entity<?> entity) {
		try {
			final Response response = azure().post(entity);

			if (Response.Status.CREATED.getStatusCode() == response.getStatus() || Response.Status.OK.getStatusCode() == response.getStatus()) {
				return response.hasEntity() ? toJsonObject(response.readEntity(String.class)) : new JsonObject();
			}
			LOGGER.error("failure, entity: {}", response.readEntity(String.class));

		} catch (final Exception e) {
			LOGGER.error("failure, while {}, exceprion: {}", this.getClass(), ExceptionUtils.getStackTrace(e));
		}
		return null;
	}

	@Loggable(Loggable.DEBUG)
	public JsonObject create(Object object) {
		return create(Entity.json(object));
	}

	public JsonObject createOrUpdate(Entity<?> entity) {
		try {
			final Response response = azure().put(entity);

			if (Response.Status.CREATED.getStatusCode() == response.getStatus() || Response.Status.OK.getStatusCode() == response.getStatus()) {
				return response.hasEntity() ? toJsonObject(response.readEntity(String.class)) : new JsonObject();
			}
			LOGGER.error("failure, entity: {}", response.readEntity(String.class));

		} catch (final Exception e) {
			LOGGER.error("failure, while {}, exceprion: {}", this.getClass(), ExceptionUtils.getStackTrace(e));
		}
		return null;
	}

	@Loggable(Loggable.DEBUG)
	public JsonObject createOrUpdate(Object object) {
		return createOrUpdate(Entity.json(object));
	}

	@Loggable(Loggable.DEBUG)
	public JsonObject delete() {
		try {

			final Response response = azure().delete();

			if (Response.Status.NO_CONTENT.getStatusCode() != response.getStatus() && LOGGER.isErrorEnabled()) {
				LOGGER.error("failure, entity: {}", response.readEntity(String.class)); //$NON-NLS-1$
			}

			return response.hasEntity() ? toJsonObject(response.readEntity(String.class)) : new JsonObject();

		} catch (final Exception e) {
			LOGGER.error("failure, while {}, exceprion: {}", this.getClass(), ExceptionUtils.getStackTrace(e));
		}
		return null;
	}

	public JsonObject get() {
		try {
			final Response response = azure().get();

			if (Response.Status.OK.getStatusCode() != response.getStatus() && LOGGER.isErrorEnabled()) {
				LOGGER.error("failure, entity: {}", response.readEntity(String.class)); //$NON-NLS-1$
			}

			return response.hasEntity() ? toJsonObject(response.readEntity(String.class)) : new JsonObject();

		} catch (final Exception e) {
			LOGGER.error("failure, while {}, exceprion: {}", this.getClass(), ExceptionUtils.getStackTrace(e));
		}
		return null;
	}

	private String getApiKey() {
		return apiKey;
	}

	private String getSearchAppName() {
		return searchAppName;
	}

	private void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	private void setSearchAppName(String searchAppName) {
		this.searchAppName = searchAppName;
	}
}
