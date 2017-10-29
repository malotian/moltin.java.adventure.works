package com.moltin.api.v2;

import java.io.File;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.glassfish.jersey.client.JerseyInvocation.Builder;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.jcabi.aspects.Loggable;
import com.moltin.adventure.works.app.Context;
import com.rest.RestRequest;

public class MoltinRequest extends RestRequest {
	static final Logger LOGGER = LoggerFactory.getLogger(MoltinRequest.class);

	String url = StringUtils.EMPTY;

	public MoltinRequest(final String... urlPath) {
		url = "https://api.moltin.com/v2/" + StringUtils.join(urlPath, '/');
	}

	protected MoltinRequest() {
	}

	MoltinRequest withUrl(String url) {
		this.url = url;
		return this;
	}

	public JsonObject create(final Entity<?> entity) {
		try {
			final Response response = moltin().post(entity);

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
	public JsonObject create(final Object object) {
		return create(Entity.json(object));
	}

	@Loggable(Loggable.DEBUG)
	public JsonObject delete() {
		try {

			final Response response = moltin().delete();

			if (Response.Status.NO_CONTENT.getStatusCode() != response.getStatus() && LOGGER.isErrorEnabled()) {
				LOGGER.error("failure, entity: {}", response.readEntity(String.class)); //$NON-NLS-1$
			}

			return response.hasEntity() ? toJsonObject(response.readEntity(String.class)) : new JsonObject();

		} catch (final Exception e) {
			LOGGER.error("failure, while {}, exceprion: {}", this.getClass(), ExceptionUtils.getStackTrace(e));
		}
		return null;
	}

	public JsonObject file(final File file) {
		@SuppressWarnings("resource")
		final MultiPart multipart = new FormDataMultiPart().field("public", "true").bodyPart(new FileDataBodyPart("file", file));
		return create(Entity.entity(multipart, multipart.getMediaType()));
	}

	public JsonObject get() {
		try {
			final Response response = moltin().get();

			if (Response.Status.OK.getStatusCode() != response.getStatus() && LOGGER.isErrorEnabled()) {
				LOGGER.error("failure, entity: {}", response.readEntity(String.class)); //$NON-NLS-1$
			}

			JsonObject result = response.hasEntity() ? toJsonObject(response.readEntity(String.class)) : new JsonObject();
			JsonObject result2 = result;

			while (result2.has("links") && null != result2.getAsJsonObject("links").get("next") && !result2.getAsJsonObject("links").get("next").isJsonNull()) {
				if (result2.getAsJsonObject("links").get("next").equals(result2.getAsJsonObject("links").get("current")))
					break;
				result2 = new MoltinRequest().withUrl(result2.getAsJsonObject("links").get("next").getAsString()).get();
				result.getAsJsonArray("data").addAll(result2.getAsJsonArray("data"));
			}

			return result;

		} catch (final Exception e) {
			LOGGER.error("failure, while {}, exceprion: {}", this.getClass(), ExceptionUtils.getStackTrace(e));
		}
		return null;
	}

	Builder moltin() {
		final AuthenticateResponse ar = Context.get(AuthenticateResponse.class);
		return client().target(url).request() // $NON-NLS-1$
				.header("Authorization", ar.getTokenType() + " " + ar.getAccessToken());
	}

}
