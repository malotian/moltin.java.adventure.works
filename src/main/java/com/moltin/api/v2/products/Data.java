
package com.moltin.api.v2.products;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "type", "name", "slug", "sku", "description", "manage_stock", "price", "status", "commodity_type", "dimensions", "weight" })
public class Data {

	@JsonProperty("commodity_type")
	private String commodityType;
	@JsonProperty("description")
	private String description;
	@JsonProperty("dimensions")
	private List<Dimension> dimensions = new ArrayList<>();
	@JsonProperty("manage_stock")
	private boolean manageStock;
	@JsonProperty("name")
	private String name;
	@JsonProperty("price")
	private List<Price> price = new ArrayList<>();
	@JsonProperty("sku")
	private String sku;
	@JsonProperty("slug")
	private String slug;
	@JsonProperty("status")
	private String status;
	@JsonProperty("type")
	private String type;
	@JsonProperty("weight")
	private Weight weight;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public Data() {
	}

	/**
	 *
	 * @param weight
	 * @param price
	 * @param status
	 * @param description
	 * @param manageStock
	 * @param name
	 * @param dimensions
	 * @param commodityType
	 * @param slug
	 * @param sku
	 * @param type
	 */
	public Data(final String type, final String name, final String slug, final String sku, final String description, final boolean manageStock, final List<Price> price,
			final String status, final String commodityType, final List<Dimension> dimensions, final Weight weight) {
		super();
		this.type = type;
		this.name = name;
		this.slug = slug;
		this.sku = sku;
		this.description = description;
		this.manageStock = manageStock;
		this.price = price;
		this.status = status;
		this.commodityType = commodityType;
		this.dimensions = dimensions;
		this.weight = weight;
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
		return new EqualsBuilder().append(weight, rhs.weight).append(price, rhs.price).append(status, rhs.status).append(description, rhs.description)
				.append(manageStock, rhs.manageStock).append(name, rhs.name).append(dimensions, rhs.dimensions).append(commodityType, rhs.commodityType).append(slug, rhs.slug)
				.append(sku, rhs.sku).append(type, rhs.type).isEquals();
	}

	@JsonProperty("commodity_type")
	public String getCommodityType() {
		return commodityType;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("dimensions")
	public List<Dimension> getDimensions() {
		return dimensions;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("price")
	public List<Price> getPrice() {
		return price;
	}

	@JsonProperty("sku")
	public String getSku() {
		return sku;
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

	@JsonProperty("weight")
	public Weight getWeight() {
		return weight;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(weight).append(price).append(status).append(description).append(manageStock).append(name).append(dimensions).append(commodityType)
				.append(slug).append(sku).append(type).toHashCode();
	}

	@JsonProperty("manage_stock")
	public boolean isManageStock() {
		return manageStock;
	}

	@JsonProperty("commodity_type")
	public void setCommodityType(final String commodityType) {
		this.commodityType = commodityType;
	}

	@JsonProperty("description")
	public void setDescription(final String description) {
		this.description = description;
	}

	@JsonProperty("dimensions")
	public void setDimensions(final List<Dimension> dimensions) {
		this.dimensions = dimensions;
	}

	@JsonProperty("manage_stock")
	public void setManageStock(final boolean manageStock) {
		this.manageStock = manageStock;
	}

	@JsonProperty("name")
	public void setName(final String name) {
		this.name = name;
	}

	@JsonProperty("price")
	public void setPrice(final List<Price> price) {
		this.price = price;
	}

	@JsonProperty("sku")
	public void setSku(final String sku) {
		this.sku = sku;
	}

	@JsonProperty("slug")
	public void setSlug(final String slug) {
		this.slug = slug;
	}

	@JsonProperty("status")
	public void setStatus(final String status) {
		this.status = status;
	}

	@JsonProperty("type")
	public void setType(final String type) {
		this.type = type;
	}

	@JsonProperty("weight")
	public void setWeight(final Weight weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("type", type).append("name", name).append("slug", slug).append("sku", sku).append("description", description)
				.append("manageStock", manageStock).append("price", price).append("status", status).append("commodityType", commodityType).append("dimensions", dimensions)
				.append("weight", weight).toString();
	}

	public Data withCommodityType(final String commodityType) {
		this.commodityType = commodityType;
		return this;
	}

	public Data withDescription(final String description) {
		this.description = description;
		return this;
	}

	public Data withDimensions(final List<Dimension> dimensions) {
		this.dimensions = dimensions;
		return this;
	}

	public Data withManageStock(final boolean manageStock) {
		this.manageStock = manageStock;
		return this;
	}

	public Data withName(final String name) {
		this.name = name;
		return this;
	}

	public Data withPrice(final List<Price> price) {
		this.price = price;
		return this;
	}

	public Data withSku(final String sku) {
		this.sku = sku;
		return this;
	}

	public Data withSlug(final String slug) {
		this.slug = slug;
		return this;
	}

	public Data withStatus(final String status) {
		this.status = status;
		return this;
	}

	public Data withType(final String type) {
		this.type = type;
		return this;
	}

	public Data withWeight(final Weight weight) {
		this.weight = weight;
		return this;
	}

}
