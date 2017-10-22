
package com.moltin.api.v2.files;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "public", "file" })
public class File {

	@JsonProperty("public")
	private boolean _public;
	@JsonProperty("file")
	private Object file;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public File() {
	}

	/**
	 *
	 * @param _public
	 * @param file
	 */
	public File(boolean _public, Object file) {
		super();
		this._public = _public;
		this.file = file;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof File == false) {
			return false;
		}
		final File rhs = (File) other;
		return new EqualsBuilder().append(_public, rhs._public).append(file, rhs.file).isEquals();
	}

	@JsonProperty("file")
	public Object getFile() {
		return file;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(_public).append(file).toHashCode();
	}

	@JsonProperty("public")
	public boolean isPublic() {
		return _public;
	}

	@JsonProperty("file")
	public void setFile(Object file) {
		this.file = file;
	}

	@JsonProperty("public")
	public void setPublic(boolean _public) {
		this._public = _public;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("_public", _public).append("file", file).toString();
	}

	public File withFile(Object file) {
		this.file = file;
		return this;
	}

	public File withPublic(boolean _public) {
		this._public = _public;
		return this;
	}

}
