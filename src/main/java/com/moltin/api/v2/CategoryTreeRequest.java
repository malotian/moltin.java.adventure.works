package com.moltin.api.v2;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.jcabi.aspects.Loggable;
import com.moltin.adventure.works.Context;
import com.moltin.adventure.works.WSRequest;

public class CategoryTreeRequest extends WSRequest {
	static final Logger LOGGER = LoggerFactory.getLogger(CategoryTreeRequest.class);

	@Loggable(Loggable.DEBUG)
	public synchronized JsonObject request() {
		try {
			final AuthenticateResponse ar = Context.get(AuthenticateResponse.class);

			final Response response = client().target("https://api.moltin.com/v2/categories/tree").request() // $NON-NLS-1$
					.header("Authorization", ar.getTokenType() + " " + ar.getAccessToken()).get(); // $NON-NLS-3$

			if (Response.Status.OK.getStatusCode() != response.getStatus() && LOGGER.isErrorEnabled()) {
				LOGGER.error("failure, entity: {}", response.readEntity(String.class)); //$NON-NLS-1$
			}

			return toJsonObject(response.readEntity(String.class));

		} catch (final Exception e) {
			LOGGER.error("failure, while creating category, exceprion: {}", ExceptionUtils.getStackTrace(e));
			return null;
		}
	}
}
