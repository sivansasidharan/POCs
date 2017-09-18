package com.ss.nitro.analytics.assetlc.domain;

import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

@QueryResult
public interface CallOne {
	@ResultColumn("totalAssetValue")
	public double getTotalAssetValue();

	@ResultColumn("totalAssetCount")
	public double getTotalAssetCount();

	@ResultColumn("poamount")
	public double getPoAmount();

	@ResultColumn("year")
	public String getYear();

	@ResultColumn("sl")
	public String getSl();

	@ResultColumn("ssl")
	public String getSsl();

	@ResultColumn("brand")
	public String getBrand();

	@ResultColumn("vendor")
	public String getVendor();

	@ResultColumn("product")
	public String getProduct();

}
