
package com.moltin.api.v2.modifiers;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "type", "modifier_type", "value" })
public class Data {

	@JsonProperty("modifier_type")
	private String modifierType;
	@JsonProperty("type")
	private String type;
	@JsonProperty("value")
	private String value;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public Data() {
	}

	/**
	 *
	 * @param modifierType
	 * @param value
	 * @param type
	 */
	public Data(final String type, final String modifierType, final String value) {
		super();
		this.type = type;
		this.modifierType = modifierType;
		this.value = value;
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
		return new EqualsBuilder().append(modifierType, rhs.modifierType).append(value, rhs.value).append(type, rhs.type).isEquals();
	}

	@JsonProperty("modifier_type")
	public String getModifierType() {
		return modifierType;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("value")
	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(modifierType).append(value).append(type).toHashCode();
	}

	@JsonProperty("modifier_type")
	public void setModifierType(final String modifierType) {
		this.modifierType = modifierType;
	}

	@JsonProperty("type")
	public void setType(final String type) {
		this.type = type;
	}

	@JsonProperty("value")
	public void setValue(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("type", type).append("modifierType", modifierType).append("value", value).toString();
	}

	public Data withModifierType(final String modifierType) {
		this.modifierType = modifierType;
		return this;
	}

	public Data withType(final String type) {
		this.type = type;
		return this;
	}

	public Data withValue(final String value) {
		this.value = value;
		return this;
	}

}
