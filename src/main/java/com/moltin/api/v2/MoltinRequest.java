package com.moltin.api.v2;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.glassfish.jersey.client.JerseyInvocation.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.jcabi.aspects.Loggable;
import com.moltin.adventure.works.Context;
import com.moltin.adventure.works.WSRequest;

public class MoltinRequest extends WSRequest {
	static final Logger LOGGER = LoggerFactory.getLogger(MoltinRequest.class);

	public static void main(String[] args) {
		final MoltinRequest mr = new MoltinRequest("products", "variations");
		System.out.println(mr.url);
	}

	String url = StringUtils.EMPTY;

	public MoltinRequest(String... urlPath) {
		url = "https://api.moltin.com/v2/" + StringUtils.join(urlPath, '/');
	}

	@Loggable(Loggable.DEBUG)
	public JsonObject create(Object object) {
		try {
			final Response response = moltin().post(Entity.json(object));

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

	public JsonObject get() {
		try {
			final Response response = moltin().get();

			if (Response.Status.OK.getStatusCode() != response.getStatus() && LOGGER.isErrorEnabled()) {
				LOGGER.error("failure, entity: {}", response.readEntity(String.class)); //$NON-NLS-1$
			}

			return response.hasEntity() ? toJsonObject(response.readEntity(String.class)) : new JsonObject();

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
