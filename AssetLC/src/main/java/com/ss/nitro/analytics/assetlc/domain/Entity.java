package com.ss.nitro.analytics.assetlc.domain;

import java.util.List;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

@NodeEntity
public class Entity {
	@GraphId
	Long id;
	@Indexed(unique = true)
	private String entity;
	private String serviceLineSubServiceLine;
	private String region;
	private String country;
	private String location;
	private String totalAssetCount;
	private String totalAssetValue;
	private String pOAmount;
	private String year;
	private List<Integer> yearList;

	public List<Integer> getYearList() {
		return yearList;
	}

	public void setYearList(List<Integer> yearList) {
		this.yearList = yearList;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getServiceLineSubServiceLine() {
		return serviceLineSubServiceLine;
	}

	public void setServiceLineSubServiceLine(String serviceLineSubServiceLine) {
		this.serviceLineSubServiceLine = serviceLineSubServiceLine;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTotalAssetCount() {
		return totalAssetCount;
	}

	public void setTotalAssetCount(String totalAssetCount) {
		this.totalAssetCount = totalAssetCount;
	}

	public String getTotalAssetValue() {
		return totalAssetValue;
	}

	public void setTotalAssetValue(String assetValue) {
		this.totalAssetValue = assetValue;
	}

	public String getpOAmount() {
		return pOAmount;
	}

	public void setpOAmount(String pOAmount) {
		this.pOAmount = pOAmount;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@QueryResult
	public class OnLoad {
		@ResultColumn("totalAssetValue")
		public int totalAssetValue;
		@ResultColumn("totalAssetCount")
		public int totalAssetCount;
		@ResultColumn("Entity")
		public List<String> entity;
		@ResultColumn("ServiceLine")
		public List<String> serviceLines;
		@ResultColumn("SubServiceLine")
		public List<String> subServiceLines;
	}

	@QueryResult
	public class CallOne {
		@ResultColumn("totalAssetValue")
		public int totalAssetValue;
		@ResultColumn("totalAssetCount")
		public int totalAssetCount;
		@ResultColumn("year")
		public int year;
		@ResultColumn("sl")
		public String serviceLine;
		@ResultColumn("ssl")
		public String subServiceLines;
		@ResultColumn("brand")
		public String brand;
		@ResultColumn("vendor")
		public String vendor;
		@ResultColumn("product")
		public String product;

	}
}
