package com.invixo.socialmedia.beans;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "sourceKey", "source", "countOfMsgs" })
public class Table_01_schema_csv {
	private String sourceKey, source, countOfMsgs;

	public String getSourceKey() {
		return sourceKey;
	}

	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCountOfMsgs() {
		return countOfMsgs;
	}

	public void setCountOfMsgs(String countOfMsgs) {
		this.countOfMsgs = countOfMsgs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sourceKey == null) ? 0 : sourceKey.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((countOfMsgs == null) ? 0 : countOfMsgs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table_01_schema_csv other = new Table_01_schema_csv();
		if (sourceKey == null) {
			if (other.sourceKey != null)
				return false;
		} else if (!sourceKey.equals(other.sourceKey))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (countOfMsgs == null) {
			if (other.countOfMsgs != null)
				return false;
		} else if (!countOfMsgs.equals(other.countOfMsgs))
			return false;
		return true;
	}

}
