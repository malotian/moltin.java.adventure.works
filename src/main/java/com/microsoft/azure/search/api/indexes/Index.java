
package com.microsoft.azure.search.api.indexes;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "fields", "scoringProfiles", "defaultScoringProfile", "corsOptions", "suggesters" })
public class Index {

	@JsonProperty("corsOptions")
	@Valid
	private CorsOptions corsOptions;
	@JsonProperty("defaultScoringProfile")
	private String defaultScoringProfile;
	@JsonProperty("fields")
	@Valid
	private List<Field> fields = new ArrayList<>();
	@JsonProperty("name")
	private String name;
	@JsonProperty("scoringProfiles")
	@Valid
	private List<Object> scoringProfiles = new ArrayList<>();
	@JsonProperty("suggesters")
	@Valid
	private List<Object> suggesters = new ArrayList<>();

	@Override
	public boolean equals(final Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Index == false) {
			return false;
		}
		final Index rhs = (Index) other;
		return new EqualsBuilder().append(defaultScoringProfile, rhs.defaultScoringProfile).append(name, rhs.name).append(scoringProfiles, rhs.scoringProfiles)
				.append(suggesters, rhs.suggesters).append(corsOptions, rhs.corsOptions).append(fields, rhs.fields).isEquals();
	}

	@JsonProperty("corsOptions")
	public CorsOptions getCorsOptions() {
		return corsOptions;
	}

	@JsonProperty("defaultScoringProfile")
	public String getDefaultScoringProfile() {
		return defaultScoringProfile;
	}

	@JsonProperty("fields")
	public List<Field> getFields() {
		return fields;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("scoringProfiles")
	public List<Object> getScoringProfiles() {
		return scoringProfiles;
	}

	@JsonProperty("suggesters")
	public List<Object> getSuggesters() {
		return suggesters;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(defaultScoringProfile).append(name).append(scoringProfiles).append(suggesters).append(corsOptions).append(fields).toHashCode();
	}

	@JsonProperty("corsOptions")
	public void setCorsOptions(final CorsOptions corsOptions) {
		this.corsOptions = corsOptions;
	}

	@JsonProperty("defaultScoringProfile")
	public void setDefaultScoringProfile(final String defaultScoringProfile) {
		this.defaultScoringProfile = defaultScoringProfile;
	}

	@JsonProperty("fields")
	public void setFields(final List<Field> fields) {
		this.fields = fields;
	}

	@JsonProperty("name")
	public void setName(final String name) {
		this.name = name;
	}

	@JsonProperty("scoringProfiles")
	public void setScoringProfiles(final List<Object> scoringProfiles) {
		this.scoringProfiles = scoringProfiles;
	}

	@JsonProperty("suggesters")
	public void setSuggesters(final List<Object> suggesters) {
		this.suggesters = suggesters;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("name", name).append("fields", fields).append("scoringProfiles", scoringProfiles)
				.append("defaultScoringProfile", defaultScoringProfile).append("corsOptions", corsOptions).append("suggesters", suggesters).toString();
	}

	public Index withCorsOptions(final CorsOptions corsOptions) {
		this.corsOptions = corsOptions;
		return this;
	}

	public Index withDefaultScoringProfile(final String defaultScoringProfile) {
		this.defaultScoringProfile = defaultScoringProfile;
		return this;
	}

	public Index withFields(final List<Field> fields) {
		this.fields = fields;
		return this;
	}

	public Index withName(final String name) {
		this.name = name;
		return this;
	}

	public Index withScoringProfiles(final List<Object> scoringProfiles) {
		this.scoringProfiles = scoringProfiles;
		return this;
	}

	public Index withSuggesters(final List<Object> suggesters) {
		this.suggesters = suggesters;
		return this;
	}

}
