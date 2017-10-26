
package com.moltin.api.v2.options;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "type", "name", "description" })
public class Data {

	@JsonProperty("description")
	private String description;
	@JsonProperty("name")
	private String name;
	@JsonProperty("type")
	private String type;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public Data() {
	}

	/**
	 *
	 * @param description
	 * @param name
	 * @param type
	 */
	public Data(final String type, final String name, final String description) {
		super();
		this.type = type;
		this.name = name;
		this.description = description;
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
		return new EqualsBuilder().append(description, rhs.description).append(name, rhs.name).append(type, rhs.type).isEquals();
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(description).append(name).append(type).toHashCode();
	}

	@JsonProperty("description")
	public void setDescription(final String description) {
		this.description = description;
	}

	@JsonProperty("name")
	public void setName(final String name) {
		this.name = name;
	}

	@JsonProperty("type")
	public void setType(final String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("type", type).append("name", name).append("description", description).toString();
	}

	public Data withDescription(final String description) {
		this.description = description;
		return this;
	}

	public Data withName(final String name) {
		this.name = name;
		return this;
	}

	public Data withType(final String type) {
		this.type = type;
		return this;
	}

}
