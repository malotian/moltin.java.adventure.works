
package com.moltin.api.v2.products;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "amount", "currency", "includes_tax" })
public class Price {

	@JsonProperty("amount")
	private int amount;
	@JsonProperty("currency")
	private String currency;
	@JsonProperty("includes_tax")
	private boolean includesTax;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public Price() {
	}

	/**
	 *
	 * @param amount
	 * @param includesTax
	 * @param currency
	 */
	public Price(final int amount, final String currency, final boolean includesTax) {
		super();
		this.amount = amount;
		this.currency = currency;
		this.includesTax = includesTax;
	}

	@Override
	public boolean equals(final Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Price == false) {
			return false;
		}
		final Price rhs = (Price) other;
		return new EqualsBuilder().append(amount, rhs.amount).append(includesTax, rhs.includesTax).append(currency, rhs.currency).isEquals();
	}

	@JsonProperty("amount")
	public int getAmount() {
		return amount;
	}

	@JsonProperty("currency")
	public String getCurrency() {
		return currency;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(amount).append(includesTax).append(currency).toHashCode();
	}

	@JsonProperty("includes_tax")
	public boolean isIncludesTax() {
		return includesTax;
	}

	@JsonProperty("amount")
	public void setAmount(final int amount) {
		this.amount = amount;
	}

	@JsonProperty("currency")
	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	@JsonProperty("includes_tax")
	public void setIncludesTax(final boolean includesTax) {
		this.includesTax = includesTax;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("amount", amount).append("currency", currency).append("includesTax", includesTax).toString();
	}

	public Price withAmount(final int amount) {
		this.amount = amount;
		return this;
	}

	public Price withCurrency(final String currency) {
		this.currency = currency;
		return this;
	}

	public Price withIncludesTax(final boolean includesTax) {
		this.includesTax = includesTax;
		return this;
	}

}
