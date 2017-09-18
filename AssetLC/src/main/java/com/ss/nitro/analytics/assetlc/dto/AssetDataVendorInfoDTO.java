package com.ss.nitro.analytics.assetlc.dto;

import java.math.BigDecimal;

public class AssetDataVendorInfoDTO {

	public double totalAssetValue;

	public double totalAssetCount;

	public double POAmount;

	public String serviceLine;

	public String subServiceLine;

	public String brand;

	public String vendor;

	public String product;

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

	public BigDecimal getPOAmount() {
		return BigDecimal.valueOf(POAmount);
	}

	public void setPOAmount(double pOAmount) {
		POAmount = pOAmount;
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

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "AssetDataVendorInfoDTO [totalAssetValue=" + totalAssetValue
				+ ", totalAssetCount=" + totalAssetCount + ", POAmount="
				+ POAmount + ", serviceLine=" + serviceLine
				+ ", subServiceLine=" + subServiceLine + ", brand=" + brand
				+ ", vendor=" + vendor + ", product=" + product + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(POAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result
				+ ((serviceLine == null) ? 0 : serviceLine.hashCode());
		result = prime * result
				+ ((subServiceLine == null) ? 0 : subServiceLine.hashCode());
		temp = Double.doubleToLongBits(totalAssetCount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalAssetValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((vendor == null) ? 0 : vendor.hashCode());
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
		AssetDataVendorInfoDTO other = (AssetDataVendorInfoDTO) obj;
		if (Double.doubleToLongBits(POAmount) != Double
				.doubleToLongBits(other.POAmount))
			return false;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
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
		if (vendor == null) {
			if (other.vendor != null)
				return false;
		} else if (!vendor.equals(other.vendor))
			return false;
		return true;
	}

}
