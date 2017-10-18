
package com.moltin.api.v2.relationships;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "data" })
public class ParentRelationship {

	@JsonProperty("data")
	private Data data;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public ParentRelationship() {
	}

	/**
	 *
	 * @param data
	 */
	public ParentRelationship(Data data) {
		super();
		this.data = data;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof ParentRelationship == false) {
			return false;
		}
		final ParentRelationship rhs = (ParentRelationship) other;
		return new EqualsBuilder().append(data, rhs.data).isEquals();
	}

	@JsonProperty("data")
	public Data getData() {
		return data;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(data).toHashCode();
	}

	@JsonProperty("data")
	public void setData(Data data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("data", data).toString();
	}

	public ParentRelationship withData(Data data) {
		this.data = data;
		return this;
	}

}
