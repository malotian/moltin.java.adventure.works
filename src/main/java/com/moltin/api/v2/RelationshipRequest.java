package com.moltin.api.v2;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcabi.aspects.Loggable;
import com.moltin.adventure.works.Context;
import com.moltin.adventure.works.WSRequest;
import com.moltin.api.v2.categories.Category;

public class RelationshipRequest extends WSRequest {
	static final Logger LOGGER = LoggerFactory.getLogger(RelationshipRequest.class);

	@Loggable(Loggable.DEBUG)
	public synchronized boolean requestCategoryRelatinship(Category category) {
		try {
			final AuthenticateResponse ar = Context.get(AuthenticateResponse.class);

			LOGGER.info("sending, creating category request");
			final Response response = client().target("https://api.moltin.com/v2/categories").request() // $NON-NLS-1$
					.header("Authorization", ar.getTokenType() + " " + ar.getAccessToken()).post(Entity.json(category)); // $NON-NLS-3$

			if (Response.Status.CREATED.getStatusCode() != response.getStatus() && LOGGER.isErrorEnabled()) {
				LOGGER.error("failure, response: {}", response); //$NON-NLS-1$
				LOGGER.error("failure, entity: {}", response.readEntity(String.class)); //$NON-NLS-1$
			}

			LOGGER.info("recieved, category response: {}", response);
			return true;
		} catch (final Exception e) {
			LOGGER.error("failure, while creating category, exceprion: {}", ExceptionUtils.getStackTrace(e));
			return false;
		}
	}
}
