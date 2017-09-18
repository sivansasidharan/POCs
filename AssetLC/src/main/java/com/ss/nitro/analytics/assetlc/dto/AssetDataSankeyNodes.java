package com.ss.nitro.analytics.assetlc.dto;

import java.util.List;

public class AssetDataSankeyNodes {
	public List<String> subServiceLines;
	public List<String> brand;
	public List<String> vendor;
	public List<String> product;
	
	public List<String> getSubServiceLines() {
		return subServiceLines;
	}
	public void setSubServiceLines(List<String> subServiceLines) {
		this.subServiceLines = subServiceLines;
	}
	public List<String> getBrand() {
		return brand;
	}
	public void setBrand(List<String> brand) {
		this.brand = brand;
	}
	public List<String> getVendor() {
		return vendor;
	}
	public void setVendor(List<String> vendor) {
		this.vendor = vendor;
	}
	public List<String> getProduct() {
		return product;
	}
	public void setProduct(List<String> product) {
		this.product = product;
	}
	@Override
	public String toString() {
		return "AssetDataSankeyNodes [subServiceLines=" + subServiceLines
				+ ", brand=" + brand + ", vendor=" + vendor + ", product="
				+ product + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result
				+ ((subServiceLines == null) ? 0 : subServiceLines.hashCode());
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
		AssetDataSankeyNodes other = (AssetDataSankeyNodes) obj;
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
		if (subServiceLines == null) {
			if (other.subServiceLines != null)
				return false;
		} else if (!subServiceLines.equals(other.subServiceLines))
			return false;
		if (vendor == null) {
			if (other.vendor != null)
				return false;
		} else if (!vendor.equals(other.vendor))
			return false;
		return true;
	}
	

}
