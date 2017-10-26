
package com.moltin.api.v2.products.relationships.categories;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "type", "id" })
public class Datum {

	@JsonProperty("id")
	private String id;
	@JsonProperty("type")
	private String type;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public Datum() {
	}

	/**
	 *
	 * @param id
	 * @param type
	 */
	public Datum(final String type, final String id) {
		super();
		this.type = type;
		this.id = id;
	}

	@Override
	public boolean equals(final Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Datum == false) {
			return false;
		}
		final Datum rhs = (Datum) other;
		return new EqualsBuilder().append(id, rhs.id).append(type, rhs.type).isEquals();
	}

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).append(type).toHashCode();
	}

	@JsonProperty("id")
	public void setId(final String id) {
		this.id = id;
	}

	@JsonProperty("type")
	public void setType(final String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("type", type).append("id", id).toString();
	}

	public Datum withId(final String id) {
		this.id = id;
		return this;
	}

	public Datum withType(final String type) {
		this.type = type;
		return this;
	}

}
