
package com.moltin.api.v2.relationships.categories;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "type", "id" })
public class Parent {

	@JsonProperty("type")
	private String type;
	@JsonProperty("id")
	private String id;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public Parent() {
	}

	/**
	 *
	 * @param id
	 * @param type
	 */
	public Parent(String type, String id) {
		super();
		this.type = type;
		this.id = id;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Parent == false) {
			return false;
		}
		final Parent rhs = (Parent) other;
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
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("type", type).append("id", id).toString();
	}

	public Parent withId(String id) {
		this.id = id;
		return this;
	}

	public Parent withType(String type) {
		this.type = type;
		return this;
	}

}
