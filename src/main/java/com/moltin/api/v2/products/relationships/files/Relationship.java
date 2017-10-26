
package com.moltin.api.v2.products.relationships.files;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "data" })
public class Relationship {

	@JsonProperty("data")
	private List<Datum> data = new ArrayList<>();

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public Relationship() {
	}

	/**
	 *
	 * @param data
	 */
	public Relationship(final List<Datum> data) {
		super();
		this.data = data;
	}

	@Override
	public boolean equals(final Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Relationship == false) {
			return false;
		}
		final Relationship rhs = (Relationship) other;
		return new EqualsBuilder().append(data, rhs.data).isEquals();
	}

	@JsonProperty("data")
	public List<Datum> getData() {
		return data;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(data).toHashCode();
	}

	@JsonProperty("data")
	public void setData(final List<Datum> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("data", data).toString();
	}

	public Relationship withData(final List<Datum> data) {
		this.data = data;
		return this;
	}

}
