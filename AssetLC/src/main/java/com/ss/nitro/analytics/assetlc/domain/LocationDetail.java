package com.ss.nitro.analytics.assetlc.domain;

import java.util.List;

import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

@QueryResult
public interface LocationDetail {

	@ResultColumn("location")
	public String getLocation();

	@ResultColumn("state")
	public String getState();

	@ResultColumn("country")
	public String getCountry();
	
	@ResultColumn("region")
	public String getRegion();

	@ResultColumn("x")
	public String getX();

	@ResultColumn("y")
	public String getY();
	
	@ResultColumn("sublocation")
	public List<String> getSublocation();
}
