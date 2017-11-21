package com.invixo.summarizer.mapreduce;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "url", "urlNo", "document_heading" })
public class ReferenceBlogFile {
	private String urlNo;

	private String url;

	private String document_heading;

	public String getUrlNo() {
		return urlNo;
	}

	public void setUrlNo(String urlNo) {
		this.urlNo = urlNo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDocument_heading() {
		return document_heading;
	}

	public void setDocument_heading(String document_heading) {
		this.document_heading = document_heading;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((urlNo == null) ? 0 : urlNo.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((document_heading == null) ? 0 : document_heading.hashCode());

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
		ReferenceBlogFile other = new ReferenceBlogFile();
		if (urlNo == null) {
			if (other.urlNo != null)
				return false;
		} else if (!urlNo.equals(other.urlNo))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (document_heading == null) {
			if (other.document_heading != null)
				return false;
		} else if (!document_heading.equals(other.document_heading))
			return false;

		return true;

	}

}
