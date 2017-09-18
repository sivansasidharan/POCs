package com.ss.nitro.analytics.assetlc.dto;

import java.math.BigDecimal;

public class AssetDataYearInfoDTO {

	public double totalAssetValue;

	public double totalAssetCount;

	public double totalPOAmount;

	public String serviceLine;

	public String subServiceLine;

	public BigDecimal getTotalAssetValue() {// (double)
											// Math.round(totalAssetCount *
		// 100) / 100;
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

	public BigDecimal getTotalPOAmount() {
		return BigDecimal.valueOf(totalPOAmount);
	}

	public void setTotalPOAmount(double totalPOAmount) {
		this.totalPOAmount = totalPOAmount;
	}

	public String getServiceLine() {
		return serviceLine;
	}

	public void setServiceLine(String serviceLine) {
		this.serviceLine = serviceLine;
	}

	public String getSubServiceLine() {
		return subServiceLine;
	}

	public void setSubServiceLine(String subServiceLine) {
		this.subServiceLine = subServiceLine;
	}

	@Override
	public String toString() {
		return "AssetDataYearInfoDTO [totalAssetValue=" + totalAssetValue
				+ ", totalAssetCount=" + totalAssetCount + ", totalPOAmount="
				+ totalPOAmount + ", serviceLine=" + serviceLine
				+ ", subServiceLine=" + subServiceLine + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((serviceLine == null) ? 0 : serviceLine.hashCode());
		result = prime * result
				+ ((subServiceLine == null) ? 0 : subServiceLine.hashCode());
		long temp;
		temp = Double.doubleToLongBits(totalAssetCount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalAssetValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalPOAmount);
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
		AssetDataYearInfoDTO other = (AssetDataYearInfoDTO) obj;
		if (serviceLine == null) {
			if (other.serviceLine != null)
				return false;
		} else if (!serviceLine.equals(other.serviceLine))
			return false;
		if (subServiceLine == null) {
			if (other.subServiceLine != null)
				return false;
		} else if (!subServiceLine.equals(other.subServiceLine))
			return false;
		if (Double.doubleToLongBits(totalAssetCount) != Double
				.doubleToLongBits(other.totalAssetCount))
			return false;
		if (Double.doubleToLongBits(totalAssetValue) != Double
				.doubleToLongBits(other.totalAssetValue))
			return false;
		if (Double.doubleToLongBits(totalPOAmount) != Double
				.doubleToLongBits(other.totalPOAmount))
			return false;
		return true;
	}

}
