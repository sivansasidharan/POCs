package com.ss.nitro.analytics.assetlc.domain;

import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

@QueryResult
public class Sankey {
	@ResultColumn("source")
	public String source;
	@ResultColumn("target")
	public String target;
	@ResultColumn("value")
	public String value;
	@ResultColumn("value2")
	public String value2;
	@ResultColumn("level")
	public String level;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	// @ResultColumn( "totalAssetValue" )
	// public String getTotalAssetValue;
	// @ResultColumn( "totalAssetCount" )
	// public String getTotalAssetCount;
	// @ResultColumn( "poamount" )
	// public String getPoamount;

}
