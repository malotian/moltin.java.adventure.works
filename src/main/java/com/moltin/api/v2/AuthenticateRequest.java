/**
 */
package com.moltin.api.v2;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcabi.aspects.Loggable;
import com.moltin.adventure.works.Context;
import com.moltin.adventure.works.WSRequest;

public class AuthenticateRequest extends WSRequest {

	static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateRequest.class);

	@Loggable(Loggable.DEBUG)
	public synchronized boolean request() {
		try {
			final AuthenticateResponse authenticateResponse = requestUnsafe().readEntity(AuthenticateResponse.class);
			Context.put(AuthenticateResponse.class, authenticateResponse);
			return true;
		} catch (final Exception e) {
			LOGGER.error("failure, while {}, exceprion: {}", this.getClass(), ExceptionUtils.getStackTrace(e));
			return false;
		}
	}
	

	// moltin-test
	// https://accounts.moltin.com/stores/1602827953735991298
	@Loggable(Loggable.DEBUG)
	private Response requestUnsafe() {
		final Response httpAuthenticateResponse = client().target("https://api.moltin.com/oauth/access_token").request() // $NON-NLS-1$
				.post(Entity.form(new Form() // $NON-NLS
						.param("grant_type", "client_credentials") // $NON-NLS
						.param("client_id", "MyKjhjJZNUllVHFNPMeiifK7Ja0uEgGMStsSReLpJb") // $NON-NLS
						.param("client_secret", "8WQtgGKX14YKyK6mt4LSe59eirufujcc8lux4dzzuf"))); // $NON-NLS-3$
		if (Response.Status.OK.getStatusCode() != httpAuthenticateResponse.getStatus() && LOGGER.isErrorEnabled()) {
			LOGGER.error("failure, entity: {}", httpAuthenticateResponse.readEntity(String.class)); //$NON-NLS-1$
		}
		return httpAuthenticateResponse;
	}
}