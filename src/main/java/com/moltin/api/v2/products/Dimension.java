
package com.moltin.api.v2.products;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "measurement", "unit", "value" })
public class Dimension {

	@JsonProperty("measurement")
	private String measurement;
	@JsonProperty("unit")
	private String unit;
	@JsonProperty("value")
	private String value;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public Dimension() {
	}

	/**
	 *
	 * @param unit
	 * @param value
	 * @param measurement
	 */
	public Dimension(String measurement, String unit, String value) {
		super();
		this.measurement = measurement;
		this.unit = unit;
		this.value = value;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Dimension == false) {
			return false;
		}
		final Dimension rhs = (Dimension) other;
		return new EqualsBuilder().append(unit, rhs.unit).append(value, rhs.value).append(measurement, rhs.measurement).isEquals();
	}

	@JsonProperty("measurement")
	public String getMeasurement() {
		return measurement;
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
		return new HashCodeBuilder().append(unit).append(value).append(measurement).toHashCode();
	}

	@JsonProperty("measurement")
	public void setMeasurement(String measurement) {
		this.measurement = measurement;
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
		return new ToStringBuilder(this).append("measurement", measurement).append("unit", unit).append("value", value).toString();
	}

	public Dimension withMeasurement(String measurement) {
		this.measurement = measurement;
		return this;
	}

	public Dimension withUnit(String unit) {
		this.unit = unit;
		return this;
	}

	public Dimension withValue(String value) {
		this.value = value;
		return this;
	}

}
