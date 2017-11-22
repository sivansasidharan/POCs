package com.invixo.socialmedia.beans;

import java.util.List;

public class Schema_Json {
	private String sourcKey;
	private String source;
	private String keywordType;

	private List<KeyWordObjects_Json> keywordList;

	public String getSource_Key() {
		return sourcKey;
	}

	public void setSource_Key(String source_Key) {
		this.sourcKey = source_Key;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getKeywordType() {
		return keywordType;
	}

	public void setKeywordType(String keywordType) {
		this.keywordType = keywordType;
	}

	public List<KeyWordObjects_Json> getKeywordList() {
		return keywordList;
	}

	public void setKeywordList(List<KeyWordObjects_Json> keywordList) {
		this.keywordList = keywordList;
	}

}
