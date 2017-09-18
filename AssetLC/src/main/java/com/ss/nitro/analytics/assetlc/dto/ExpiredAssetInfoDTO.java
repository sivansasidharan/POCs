package com.ss.nitro.analytics.assetlc.dto;

import java.math.BigDecimal;

public class ExpiredAssetInfoDTO {

	double totalAssetValue;

	double totalAssetCount;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(totalAssetCount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalAssetValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		ExpiredAssetInfoDTO other = (ExpiredAssetInfoDTO) obj;
		if (Double.doubleToLongBits(totalAssetCount) != Double
				.doubleToLongBits(other.totalAssetCount))
			return false;
		if (Double.doubleToLongBits(totalAssetValue) != Double
				.doubleToLongBits(other.totalAssetValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ExpiredAssetInfoDTO [totalAssetValue=" + totalAssetValue
				+ ", totalAssetCount=" + totalAssetCount + "]";
	}

}