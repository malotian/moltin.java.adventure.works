
package com.microsoft.azure.search.api.indexes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "type",
    "searchable",
    "filterable",
    "retrievable",
    "sortable",
    "facetable",
    "key",
    "analyzer"
})
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

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Field withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public Field withType(String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("searchable")
    public boolean isSearchable() {
        return searchable;
    }

    @JsonProperty("searchable")
    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public Field withSearchable(boolean searchable) {
        this.searchable = searchable;
        return this;
    }

    @JsonProperty("filterable")
    public boolean isFilterable() {
        return filterable;
    }

    @JsonProperty("filterable")
    public void setFilterable(boolean filterable) {
        this.filterable = filterable;
    }

    public Field withFilterable(boolean filterable) {
        this.filterable = filterable;
        return this;
    }

    @JsonProperty("retrievable")
    public boolean isRetrievable() {
        return retrievable;
    }

    @JsonProperty("retrievable")
    public void setRetrievable(boolean retrievable) {
        this.retrievable = retrievable;
    }

    public Field withRetrievable(boolean retrievable) {
        this.retrievable = retrievable;
        return this;
    }

    @JsonProperty("sortable")
    public boolean isSortable() {
        return sortable;
    }

    @JsonProperty("sortable")
    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public Field withSortable(boolean sortable) {
        this.sortable = sortable;
        return this;
    }

    @JsonProperty("facetable")
    public boolean isFacetable() {
        return facetable;
    }

    @JsonProperty("facetable")
    public void setFacetable(boolean facetable) {
        this.facetable = facetable;
    }

    public Field withFacetable(boolean facetable) {
        this.facetable = facetable;
        return this;
    }

    @JsonProperty("key")
    public boolean isKey() {
        return key;
    }

    @JsonProperty("key")
    public void setKey(boolean key) {
        this.key = key;
    }

    public Field withKey(boolean key) {
        this.key = key;
        return this;
    }

    @JsonProperty("analyzer")
    public Object getAnalyzer() {
        return analyzer;
    }

    @JsonProperty("analyzer")
    public void setAnalyzer(Object analyzer) {
        this.analyzer = analyzer;
    }

    public Field withAnalyzer(Object analyzer) {
        this.analyzer = analyzer;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("type", type).append("searchable", searchable).append("filterable", filterable).append("retrievable", retrievable).append("sortable", sortable).append("facetable", facetable).append("key", key).append("analyzer", analyzer).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(filterable).append(sortable).append(name).append(analyzer).append(type).append(key).append(facetable).append(searchable).append(retrievable).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Field) == false) {
            return false;
        }
        Field rhs = ((Field) other);
        return new EqualsBuilder().append(filterable, rhs.filterable).append(sortable, rhs.sortable).append(name, rhs.name).append(analyzer, rhs.analyzer).append(type, rhs.type).append(key, rhs.key).append(facetable, rhs.facetable).append(searchable, rhs.searchable).append(retrievable, rhs.retrievable).isEquals();
    }

}
