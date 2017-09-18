package com.ss.nitro.analytics.assetlc.domain.sankey;

import java.util.ArrayList;
import java.util.List;

public class VendorName {

	private String name;
	private Value value;
	private List<com.ss.nitro.analytics.assetlc.domain.sankey.Brand> Brand = new ArrayList<com.ss.nitro.analytics.assetlc.domain.sankey.Brand>();

	/**
	 * 
	 * @return The value
	 */
	public Value getValue() {
		return value;
	}

	/**
	 * 
	 * @param value
	 *            The value
	 */
	public void setValue(Value value) {
		this.value = value;
	}

	/**
	 * 
	 * @return The Brand
	 */
	public List<com.ss.nitro.analytics.assetlc.domain.sankey.Brand> getBrand() {
		return Brand;
	}

	/**
	 * 
	 * @param Brand
	 *            The Brand
	 */
	public void setBrand(
			List<com.ss.nitro.analytics.assetlc.domain.sankey.Brand> Brand) {
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
