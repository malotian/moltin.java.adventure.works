
package com.microsoft.azure.search.api.indexes;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "allowedOrigins", "maxAgeInSeconds" })
public class CorsOptions {

	@JsonProperty("allowedOrigins")
	@Valid
	private List<String> allowedOrigins = new ArrayList<>();
	@JsonProperty("maxAgeInSeconds")
	private int maxAgeInSeconds;

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof CorsOptions == false) {
			return false;
		}
		final CorsOptions rhs = (CorsOptions) other;
		return new EqualsBuilder().append(maxAgeInSeconds, rhs.maxAgeInSeconds).append(allowedOrigins, rhs.allowedOrigins).isEquals();
	}

	@JsonProperty("allowedOrigins")
	public List<String> getAllowedOrigins() {
		return allowedOrigins;
	}

	@JsonProperty("maxAgeInSeconds")
	public int getMaxAgeInSeconds() {
		return maxAgeInSeconds;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(maxAgeInSeconds).append(allowedOrigins).toHashCode();
	}

	@JsonProperty("allowedOrigins")
	public void setAllowedOrigins(List<String> allowedOrigins) {
		this.allowedOrigins = allowedOrigins;
	}

	@JsonProperty("maxAgeInSeconds")
	public void setMaxAgeInSeconds(int maxAgeInSeconds) {
		this.maxAgeInSeconds = maxAgeInSeconds;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("allowedOrigins", allowedOrigins).append("maxAgeInSeconds", maxAgeInSeconds).toString();
	}

	public CorsOptions withAllowedOrigins(List<String> allowedOrigins) {
		this.allowedOrigins = allowedOrigins;
		return this;
	}

	public CorsOptions withMaxAgeInSeconds(int maxAgeInSeconds) {
		this.maxAgeInSeconds = maxAgeInSeconds;
		return this;
	}

}
