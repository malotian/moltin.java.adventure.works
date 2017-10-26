
package com.moltin.api.v2.categories.relationships.categories;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "parent" })
public class Data {

	@JsonProperty("parent")
	private Parent parent;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public Data() {
	}

	/**
	 *
	 * @param parent
	 */
	public Data(final Parent parent) {
		super();
		this.parent = parent;
	}

	@Override
	public boolean equals(final Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Data == false) {
			return false;
		}
		final Data rhs = (Data) other;
		return new EqualsBuilder().append(parent, rhs.parent).isEquals();
	}

	@JsonProperty("parent")
	public Parent getParent() {
		return parent;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(parent).toHashCode();
	}

	@JsonProperty("parent")
	public void setParent(final Parent parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("parent", parent).toString();
	}

	public Data withParent(final Parent parent) {
		this.parent = parent;
		return this;
	}

}
