
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

	@JsonProperty("name")
	private String name;
	@JsonProperty("type")
	private String type;
	@JsonProperty("searchable")
	private boolean searchable;
	@JsonProperty("filterable")
	private boolean filterable;
	@JsonProperty("retrievable")
	private boolean retrievable;
	@JsonProperty("sortable")
	private boolean sortable;
	@JsonProperty("facetable")
	private boolean facetable;
	@JsonProperty("key")
	private boolean key;
	@JsonProperty("analyzer")
	private Object analyzer;

	@Override
	public boolean equals(Object other) {
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
	public void setAnalyzer(Object analyzer) {
		this.analyzer = analyzer;
	}

	@JsonProperty("facetable")
	public void setFacetable(boolean facetable) {
		this.facetable = facetable;
	}

	@JsonProperty("filterable")
	public void setFilterable(boolean filterable) {
		this.filterable = filterable;
	}

	@JsonProperty("key")
	public void setKey(boolean key) {
		this.key = key;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("retrievable")
	public void setRetrievable(boolean retrievable) {
		this.retrievable = retrievable;
	}

	@JsonProperty("searchable")
	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	@JsonProperty("sortable")
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("name", name).append("type", type).append("searchable", searchable).append("filterable", filterable)
				.append("retrievable", retrievable).append("sortable", sortable).append("facetable", facetable).append("key", key).append("analyzer", analyzer).toString();
	}

	public Field withAnalyzer(Object analyzer) {
		this.analyzer = analyzer;
		return this;
	}

	public Field withFacetable(boolean facetable) {
		this.facetable = facetable;
		return this;
	}

	public Field withFilterable(boolean filterable) {
		this.filterable = filterable;
		return this;
	}

	public Field withKey(boolean key) {
		this.key = key;
		return this;
	}

	public Field withName(String name) {
		this.name = name;
		return this;
	}

	public Field withRetrievable(boolean retrievable) {
		this.retrievable = retrievable;
		return this;
	}

	public Field withSearchable(boolean searchable) {
		this.searchable = searchable;
		return this;
	}

	public Field withSortable(boolean sortable) {
		this.sortable = sortable;
		return this;
	}

	public Field withType(String type) {
		this.type = type;
		return this;
	}

}
