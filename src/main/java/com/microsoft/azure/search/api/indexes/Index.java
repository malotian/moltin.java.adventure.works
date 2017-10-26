
package com.microsoft.azure.search.api.indexes;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "fields",
    "scoringProfiles",
    "defaultScoringProfile",
    "corsOptions",
    "suggesters"
})
public class Index {

    @JsonProperty("name")
    private String name;
    @JsonProperty("fields")
    @Valid
    private List<Field> fields = new ArrayList<Field>();
    @JsonProperty("scoringProfiles")
    @Valid
    private List<Object> scoringProfiles = new ArrayList<Object>();
    @JsonProperty("defaultScoringProfile")
    private String defaultScoringProfile;
    @JsonProperty("corsOptions")
    @Valid
    private CorsOptions corsOptions;
    @JsonProperty("suggesters")
    @Valid
    private List<Object> suggesters = new ArrayList<Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Index withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("fields")
    public List<Field> getFields() {
        return fields;
    }

    @JsonProperty("fields")
    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public Index withFields(List<Field> fields) {
        this.fields = fields;
        return this;
    }

    @JsonProperty("scoringProfiles")
    public List<Object> getScoringProfiles() {
        return scoringProfiles;
    }

    @JsonProperty("scoringProfiles")
    public void setScoringProfiles(List<Object> scoringProfiles) {
        this.scoringProfiles = scoringProfiles;
    }

    public Index withScoringProfiles(List<Object> scoringProfiles) {
        this.scoringProfiles = scoringProfiles;
        return this;
    }

    @JsonProperty("defaultScoringProfile")
    public String getDefaultScoringProfile() {
        return defaultScoringProfile;
    }

    @JsonProperty("defaultScoringProfile")
    public void setDefaultScoringProfile(String defaultScoringProfile) {
        this.defaultScoringProfile = defaultScoringProfile;
    }

    public Index withDefaultScoringProfile(String defaultScoringProfile) {
        this.defaultScoringProfile = defaultScoringProfile;
        return this;
    }

    @JsonProperty("corsOptions")
    public CorsOptions getCorsOptions() {
        return corsOptions;
    }

    @JsonProperty("corsOptions")
    public void setCorsOptions(CorsOptions corsOptions) {
        this.corsOptions = corsOptions;
    }

    public Index withCorsOptions(CorsOptions corsOptions) {
        this.corsOptions = corsOptions;
        return this;
    }

    @JsonProperty("suggesters")
    public List<Object> getSuggesters() {
        return suggesters;
    }

    @JsonProperty("suggesters")
    public void setSuggesters(List<Object> suggesters) {
        this.suggesters = suggesters;
    }

    public Index withSuggesters(List<Object> suggesters) {
        this.suggesters = suggesters;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("fields", fields).append("scoringProfiles", scoringProfiles).append("defaultScoringProfile", defaultScoringProfile).append("corsOptions", corsOptions).append("suggesters", suggesters).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(defaultScoringProfile).append(name).append(scoringProfiles).append(suggesters).append(corsOptions).append(fields).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Index) == false) {
            return false;
        }
        Index rhs = ((Index) other);
        return new EqualsBuilder().append(defaultScoringProfile, rhs.defaultScoringProfile).append(name, rhs.name).append(scoringProfiles, rhs.scoringProfiles).append(suggesters, rhs.suggesters).append(corsOptions, rhs.corsOptions).append(fields, rhs.fields).isEquals();
    }

}
