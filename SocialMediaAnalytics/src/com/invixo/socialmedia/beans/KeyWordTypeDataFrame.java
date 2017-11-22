package com.invixo.socialmedia.beans;

import java.util.List;

public class KeyWordTypeDataFrame {

	private String keywordType;

	private List<KeywordDataFrame> keywordDataFrameList;

	public String getKeywordType() {
		return keywordType;
	}

	public void setKeywordType(String keywordType) {
		this.keywordType = keywordType;
	}

	public List<KeywordDataFrame> getKeywordDataFrameList() {
		return keywordDataFrameList;
	}

	public void setKeywordDataFrameList(List<KeywordDataFrame> keywordDataFrameList) {
		this.keywordDataFrameList = keywordDataFrameList;
	}

}
