package com.ss.nitro.analytics.assetlc.dto;

import java.math.BigDecimal;

public class AssetInfoDTO {

	String year;
	double totalAssetValue;
	double totalAssetCount;
	double POAmount;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(POAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		AssetInfoDTO other = (AssetInfoDTO) obj;
		if (Double.doubleToLongBits(POAmount) != Double
				.doubleToLongBits(other.POAmount))
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

	@Override
	public String toString() {
		return "AssetInfoDTO [year=" + year + ", totalAssetValue="
				+ totalAssetValue + ", totalAssetCount=" + totalAssetCount
				+ ", POAmount=" + POAmount + "]";
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public BigDecimal getTotalAssetValue() {

		return BigDecimal.valueOf(totalAssetValue);
	}

	public void setTotalAssetValue(double totalAssetValue) {
		this.totalAssetValue = totalAssetValue;
	}

	public BigDecimal getTotalAssetCount() {
		return BigDecimal.valueOf(totalAssetCount);
	}

	public void setTotalAssetCount(double totalAssetCount) {
		this.totalAssetCount = totalAssetCount;
	}

	public BigDecimal getPOAmount() {
		return BigDecimal.valueOf(POAmount);
	}

	public void setPOAmount(double pOAmount) {
		POAmount = pOAmount;
	}

}
