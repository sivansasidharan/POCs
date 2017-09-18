package com.ss.nitro.analytics.assetlc.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class AssetDataDTO {

	public double totalAssetValue;

	public double totalAssetCount;

	public double poAmount;

	public List<String> entity;

	public List<String> serviceLines;

	public List<String> subServiceLines;

	public List<String> region;

	public List<String> country;

	public List<String> city;

	public List<String> location;

	public Map<String, String[]> year;

	public List<String> getRegion() {
		return region;
	}

	public void setRegion(List<String> region) {
		this.region = region;
	}

	public List<String> getCountry() {
		return country;
	}

	public void setCountry(List<String> country) {
		this.country = country;
	}

	public List<String> getCity() {
		return city;
	}

	public void setCity(List<String> city) {
		this.city = city;
	}

	public List<String> getLocation() {
		return location;
	}

	public void setLocation(List<String> location) {
		this.location = location;
	}

	public double getTotalAssetValue() {
		return (double) Math.round(totalAssetValue * 100) / 100;
	}

	public void setTotalAssetValue(double totalAssetValue) {
		this.totalAssetValue = totalAssetValue;
	}

	public double getTotalAssetCount() {
		return (double) Math.round(totalAssetCount * 100) / 100;
	}

	public void setTotalAssetCount(double totalAssetCount) {
		this.totalAssetCount = totalAssetCount;
	}

	public List<String> getEntity() {
		return entity;
	}

	public void setEntity(List<String> entity) {
		this.entity = entity;
	}

	public List<String> getServiceLines() {
		return serviceLines;
	}

	public void setServiceLines(List<String> serviceLines) {
		this.serviceLines = serviceLines;
	}

	public List<String> getSubServiceLines() {
		return subServiceLines;
	}

	public void setSubServiceLines(List<String> subServiceLines) {
		this.subServiceLines = subServiceLines;
	}

	public Map<String, String[]> getYear() {
		return year;
	}

	public void setYear(Map<String, String[]> year) {
		this.year = year;
	}

	public BigDecimal getPoAmount() {
		return BigDecimal.valueOf(poAmount);
				
	}

	public void setPoAmount(double poAmount) {
		this.poAmount = poAmount;
	}

	@Override
	public String toString() {
		return "AssetDataDTO [totalAssetValue=" + totalAssetValue
				+ ", totalAssetCount=" + totalAssetCount + ", poAmount="
				+ poAmount + ", entity=" + entity + ", serviceLines="
				+ serviceLines + ", subServiceLines=" + subServiceLines
				+ ", region=" + region + ", country=" + country + ", city="
				+ city + ", location=" + location + ", year=" + year + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		long temp;
		temp = Double.doubleToLongBits(poAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result
				+ ((serviceLines == null) ? 0 : serviceLines.hashCode());
		result = prime * result
				+ ((subServiceLines == null) ? 0 : subServiceLines.hashCode());
		temp = Double.doubleToLongBits(totalAssetCount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalAssetValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssetDataDTO other = (AssetDataDTO) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (Double.doubleToLongBits(poAmount) != Double
				.doubleToLongBits(other.poAmount))
			return false;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		if (serviceLines == null) {
			if (other.serviceLines != null)
				return false;
		} else if (!serviceLines.equals(other.serviceLines))
			return false;
		if (subServiceLines == null) {
			if (other.subServiceLines != null)
				return false;
		} else if (!subServiceLines.equals(other.subServiceLines))
			return false;
		if (Double.doubleToLongBits(totalAssetCount) != Double
				.doubleToLongBits(other.totalAssetCount))
			return false;
		if (Double.doubleToLongBits(totalAssetValue) != Double
				.doubleToLongBits(other.totalAssetValue))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

}
