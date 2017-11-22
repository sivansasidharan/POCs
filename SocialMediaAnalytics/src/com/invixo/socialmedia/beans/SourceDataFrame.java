package com.invixo.socialmedia.beans;

import java.util.List;

public class SourceDataFrame {

	private String source, sourceKey;

	private List<KeyWordTypeDataFrame> keywordTypeDataFrameList;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceKey() {
		return sourceKey;
	}

	public List<KeyWordTypeDataFrame> getKeywordTypeDataFrameList() {
		return keywordTypeDataFrameList;
	}

	public void setKeywordTypeDataFrameList(List<KeyWordTypeDataFrame> keywordTypeDataFrameList) {
		this.keywordTypeDataFrameList = keywordTypeDataFrameList;
	}

	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}

}
