package com.moltin.api.v2;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "expires", "identifier", "expires_in", "access_token", "token_type" })
public class AuthenticateResponse {

	@JsonProperty("expires")
	private int expires;
	@JsonProperty("identifier")
	private String identifier;
	@JsonProperty("expires_in")
	private int expiresIn;
	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("token_type")
	private String tokenType;

	@Override
	public boolean equals(final Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof AuthenticateResponse == false) {
			return false;
		}
		final AuthenticateResponse rhs = (AuthenticateResponse) other;
		return new EqualsBuilder().append(tokenType, rhs.tokenType).append(expires, rhs.expires).append(accessToken, rhs.accessToken).append(expiresIn, rhs.expiresIn)
				.append(identifier, rhs.identifier).isEquals();
	}

	@JsonProperty("access_token")
	public String getAccessToken() {
		return accessToken;
	}

	@JsonProperty("expires")
	public int getExpires() {
		return expires;
	}

	@JsonProperty("expires_in")
	public int getExpiresIn() {
		return expiresIn;
	}

	@JsonProperty("identifier")
	public String getIdentifier() {
		return identifier;
	}

	@JsonProperty("token_type")
	public String getTokenType() {
		return tokenType;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(tokenType).append(expires).append(accessToken).append(expiresIn).append(identifier).toHashCode();
	}

	@JsonProperty("access_token")
	public void setAccessToken(final String accessToken) {
		this.accessToken = accessToken;
	}

	@JsonProperty("expires")
	public void setExpires(final int expires) {
		this.expires = expires;
	}

	@JsonProperty("expires_in")
	public void setExpiresIn(final int expiresIn) {
		this.expiresIn = expiresIn;
	}

	@JsonProperty("identifier")
	public void setIdentifier(final String identifier) {
		this.identifier = identifier;
	}

	@JsonProperty("token_type")
	public void setTokenType(final String tokenType) {
		this.tokenType = tokenType;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("expires", expires).append("identifier", identifier).append("expiresIn", expiresIn).append("accessToken", accessToken)
				.append("tokenType", tokenType).toString();
	}

	public AuthenticateResponse withAccessToken(final String accessToken) {
		this.accessToken = accessToken;
		return this;
	}

	public AuthenticateResponse withExpires(final int expires) {
		this.expires = expires;
		return this;
	}

	public AuthenticateResponse withExpiresIn(final int expiresIn) {
		this.expiresIn = expiresIn;
		return this;
	}

	public AuthenticateResponse withIdentifier(final String identifier) {
		this.identifier = identifier;
		return this;
	}

	public AuthenticateResponse withTokenType(final String tokenType) {
		this.tokenType = tokenType;
		return this;
	}

}
