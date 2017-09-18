package com.ss.nitro.analytics.assetlc.domain.expired;

import java.util.ArrayList;
import java.util.List;

public class ExVendorName {

	private String name;
	private ExValue value;
	private List<com.ss.nitro.analytics.assetlc.domain.expired.ExBrand> Brand = new ArrayList<com.ss.nitro.analytics.assetlc.domain.expired.ExBrand>();

	/**
	 * 
	 * @return The value
	 */
	public ExValue getValue() {
		return value;
	}

	/**
	 * 
	 * @param value
	 *            The value
	 */
	public void setValue(ExValue value) {
		this.value = value;
	}

	/**
	 * 
	 * @return The Brand
	 */
	public List<com.ss.nitro.analytics.assetlc.domain.expired.ExBrand> getBrand() {
		return Brand;
	}

	/**
	 * 
	 * @param Brand
	 *            The Brand
	 */
	public void setBrand(
			List<com.ss.nitro.analytics.assetlc.domain.expired.ExBrand> Brand) {
		this.Brand = Brand;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "VendorName [name=" + name + ", value=" + value + ", Brand="
				+ Brand + "]";
	}

}
