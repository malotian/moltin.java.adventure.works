
package com.moltin.api.v2.products;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "unit", "value" })
public class Weight {

	@JsonProperty("unit")
	private String unit;
	@JsonProperty("value")
	private String value;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public Weight() {
	}

	/**
	 *
	 * @param unit
	 * @param value
	 */
	public Weight(String unit, String value) {
		super();
		this.unit = unit;
		this.value = value;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Weight == false) {
			return false;
		}
		final Weight rhs = (Weight) other;
		return new EqualsBuilder().append(unit, rhs.unit).append(value, rhs.value).isEquals();
	}

	@JsonProperty("unit")
	public String getUnit() {
		return unit;
	}

	@JsonProperty("value")
	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(unit).append(value).toHashCode();
	}

	@JsonProperty("unit")
	public void setUnit(String unit) {
		this.unit = unit;
	}

	@JsonProperty("value")
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("unit", unit).append("value", value).toString();
	}

	public Weight withUnit(String unit) {
		this.unit = unit;
		return this;
	}

	public Weight withValue(String value) {
		this.value = value;
		return this;
	}

}
