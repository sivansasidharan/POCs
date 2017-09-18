package com.ss.nitro.analytics.assetlc.domain;

import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

@QueryResult
public interface Prediction {
	@ResultColumn("forecast")
	public int getForecast();

}
