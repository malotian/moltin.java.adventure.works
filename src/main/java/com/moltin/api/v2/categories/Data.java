package com.moltin.api.v2.categories;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "type", "name", "description", "slug", "status" })
public class Data {

	@JsonProperty("type")
	private String type;
	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private String description;
	@JsonProperty("slug")
	private String slug;
	@JsonProperty("status")
	private String status;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public Data() {
	}

	/**
	 *
	 * @param status
	 * @param description
	 * @param name
	 * @param slug
	 * @param type
	 */
	public Data(String type, String name, String description, String slug, String status) {
		super();
		this.type = type;
		this.name = name;
		this.description = description;
		this.slug = slug;
		this.status = status;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Data == false) {
			return false;
		}
		final Data rhs = (Data) other;
		return new EqualsBuilder().append(status, rhs.status).append(description, rhs.description).append(name, rhs.name).append(slug, rhs.slug).append(type, rhs.type).isEquals();
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("slug")
	public String getSlug() {
		return slug;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(status).append(description).append(name).append(slug).append(type).toHashCode();
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("slug")
	public void setSlug(String slug) {
		this.slug = slug;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("type", type).append("name", name).append("description", description).append("slug", slug).append("status", status).toString();
	}

	public Data withDescription(String description) {
		this.description = description;
		return this;
	}

	public Data withName(String name) {
		this.name = name;
		return this;
	}

	public Data withSlug(String slug) {
		this.slug = slug;
		return this;
	}

	public Data withStatus(String status) {
		this.status = status;
		return this;
	}

	public Data withType(String type) {
		this.type = type;
		return this;
	}

}