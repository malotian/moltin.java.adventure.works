package com.moltin.api.v2;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcabi.aspects.Loggable;
import com.moltin.adventure.works.Context;
import com.moltin.adventure.works.WSRequest;
import com.moltin.api.v2.relationships.ParentRelationship;

public class CreateCategoryCategoryRelationshipRequest extends WSRequest {
	static final Logger LOGGER = LoggerFactory.getLogger(CreateCategoryCategoryRelationshipRequest.class);

	@Loggable(Loggable.DEBUG)
	public synchronized boolean request(String uuid, ParentRelationship relationship) {
		try {
			final AuthenticateResponse ar = Context.get(AuthenticateResponse.class);
			final Response response = client().target("https://api.moltin.com/v2/categories/" + uuid + "/relationships/categories").request() // $NON-NLS-1$
					.header("Authorization", ar.getTokenType() + " " + ar.getAccessToken()).post(Entity.json(relationship)); // $NON-NLS-3$

			if (Response.Status.OK.getStatusCode() != response.getStatus() && LOGGER.isErrorEnabled()) {
				LOGGER.error("failure, entity: {}", response.readEntity(String.class)); //$NON-NLS-1$
			}

			return true;
		} catch (final Exception e) {
			LOGGER.error("failure, while creating relationship, exceprion: {}", ExceptionUtils.getStackTrace(e));
			return false;
		}
	}
}
