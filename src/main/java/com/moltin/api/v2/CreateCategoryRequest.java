package com.moltin.api.v2;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcabi.aspects.Loggable;
import com.moltin.adventure.works.Context;
import com.moltin.adventure.works.Tuple;
import com.moltin.adventure.works.WSRequest;
import com.moltin.api.v2.categories.Category;

public class CreateCategoryRequest extends WSRequest {
	static final Logger LOGGER = LoggerFactory.getLogger(CreateCategoryRequest.class);

	@Loggable(Loggable.DEBUG)
	public synchronized String request(Category category) {
		try {
			final AuthenticateResponse ar = Context.get(AuthenticateResponse.class);
			final Response response = client().target("https://api.moltin.com/v2/categories").request() // $NON-NLS-1$
					.header("Authorization", ar.getTokenType() + " " + ar.getAccessToken()).post(Entity.json(category)); // $NON-NLS-3$

			if (Response.Status.CREATED.getStatusCode() != response.getStatus() && LOGGER.isErrorEnabled()) {
				LOGGER.error("failure, entity: {}", response.readEntity(String.class)); //$NON-NLS-1$
			}
			final Tuple tuple = response.readEntity(Tuple.class);
			return tuple.getTuple("data").getString("id");

		} catch (final Exception e) {
			LOGGER.error("failure, while creating category, exceprion: {}", ExceptionUtils.getStackTrace(e));
			return null;
		}
	}
}
