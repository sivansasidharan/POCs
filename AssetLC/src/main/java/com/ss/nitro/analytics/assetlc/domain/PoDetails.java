package com.ss.nitro.analytics.assetlc.domain;

import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

@QueryResult
public interface PoDetails {

	@ResultColumn("sublocation")
	public String getSubocation();

	@ResultColumn("poamount")
	public String getPoamount();
}
