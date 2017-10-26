
package com.microsoft.azure.search.api.indexes;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "type", "searchable", "filterable", "retrievable", "sortable", "facetable", "key", "analyzer" })
public class Field {

	@JsonProperty("analyzer")
	private Object analyzer;
	@JsonProperty("facetable")
	private boolean facetable;
	@JsonProperty("filterable")
	private boolean filterable;
	@JsonProperty("key")
	private boolean key;
	@JsonProperty("name")
	private String name;
	@JsonProperty("retrievable")
	private boolean retrievable;
	@JsonProperty("searchable")
	private boolean searchable;
	@JsonProperty("sortable")
	private boolean sortable;
	@JsonProperty("type")
	private String type;

	@Override
	public boolean equals(final Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Field == false) {
			return false;
		}
		final Field rhs = (Field) other;
		return new EqualsBuilder().append(filterable, rhs.filterable).append(sortable, rhs.sortable).append(name, rhs.name).append(analyzer, rhs.analyzer).append(type, rhs.type)
				.append(key, rhs.key).append(facetable, rhs.facetable).append(searchable, rhs.searchable).append(retrievable, rhs.retrievable).isEquals();
	}

	@JsonProperty("analyzer")
	public Object getAnalyzer() {
		return analyzer;
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
		return new HashCodeBuilder().append(filterable).append(sortable).append(name).append(analyzer).append(type).append(key).append(facetable).append(searchable)
				.append(retrievable).toHashCode();
	}

	@JsonProperty("facetable")
	public boolean isFacetable() {
		return facetable;
	}

	@JsonProperty("filterable")
	public boolean isFilterable() {
		return filterable;
	}

	@JsonProperty("key")
	public boolean isKey() {
		return key;
	}

	@JsonProperty("retrievable")
	public boolean isRetrievable() {
		return retrievable;
	}

	@JsonProperty("searchable")
	public boolean isSearchable() {
		return searchable;
	}

	@JsonProperty("sortable")
	public boolean isSortable() {
		return sortable;
	}

	@JsonProperty("analyzer")
	public void setAnalyzer(final Object analyzer) {
		this.analyzer = analyzer;
	}

	@JsonProperty("facetable")
	public void setFacetable(final boolean facetable) {
		this.facetable = facetable;
	}

	@JsonProperty("filterable")
	public void setFilterable(final boolean filterable) {
		this.filterable = filterable;
	}

	@JsonProperty("key")
	public void setKey(final boolean key) {
		this.key = key;
	}

	@JsonProperty("name")
	public void setName(final String name) {
		this.name = name;
	}

	@JsonProperty("retrievable")
	public void setRetrievable(final boolean retrievable) {
		this.retrievable = retrievable;
	}

	@JsonProperty("searchable")
	public void setSearchable(final boolean searchable) {
		this.searchable = searchable;
	}

	@JsonProperty("sortable")
	public void setSortable(final boolean sortable) {
		this.sortable = sortable;
	}

	@JsonProperty("type")
	public void setType(final String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("name", name).append("type", type).append("searchable", searchable).append("filterable", filterable)
				.append("retrievable", retrievable).append("sortable", sortable).append("facetable", facetable).append("key", key).append("analyzer", analyzer).toString();
	}

	public Field withAnalyzer(final Object analyzer) {
		this.analyzer = analyzer;
		return this;
	}

	public Field withFacetable(final boolean facetable) {
		this.facetable = facetable;
		return this;
	}

	public Field withFilterable(final boolean filterable) {
		this.filterable = filterable;
		return this;
	}

	public Field withKey(final boolean key) {
		this.key = key;
		return this;
	}

	public Field withName(final String name) {
		this.name = name;
		return this;
	}

	public Field withRetrievable(final boolean retrievable) {
		this.retrievable = retrievable;
		return this;
	}

	public Field withSearchable(final boolean searchable) {
		this.searchable = searchable;
		return this;
	}

	public Field withSortable(final boolean sortable) {
		this.sortable = sortable;
		return this;
	}

	public Field withType(final String type) {
		this.type = type;
		return this;
	}

}
