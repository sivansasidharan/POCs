package com.ss.nitro.analytics.assetlc.domain;

import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

@QueryResult
public interface ExpiredInfo {
	@ResultColumn("totalAssetValue")
	public double getTotalAssetValue();

	@ResultColumn("totalAssetCount")
	public double getTotalAssetCount();

}
