package com.ss.nitro.analytics.assetlc.domain;

import java.util.List;

import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

@QueryResult
public interface OnLoad {
	@ResultColumn("totalAssetValue")
	public double getTotalAssetValue();

	@ResultColumn("totalAssetCount")
	public double getTotalAssetCount();

	@ResultColumn("poamount")
	public double getPoAmount();

	@ResultColumn("Entity")
	public List<String> getEntity();

	@ResultColumn("ServiceLine")
	public List<String> getServiceLine();

	@ResultColumn("SubServiceLine")
	public List<String> getSubServiceLine();

	@ResultColumn("Region")
	public List<String> getRegion();

	@ResultColumn("Country")
	public List<String> getCountry();

	@ResultColumn("City")
	public List<String> getCity();

	@ResultColumn("Location")
	public List<String> getLocation();

	@ResultColumn("brand")
	public List<String> getBrand();

	@ResultColumn("vendor")
	public List<String> getVendor();

	@ResultColumn("product")
	public List<String> getProduct();
}
