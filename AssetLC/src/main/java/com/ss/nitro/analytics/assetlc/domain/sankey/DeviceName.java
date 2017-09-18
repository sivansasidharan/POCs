package com.ss.nitro.analytics.assetlc.domain.sankey;

import java.util.ArrayList;
import java.util.List;

public class DeviceName {

	private String name;
	private Value value;
	private List<com.ss.nitro.analytics.assetlc.domain.sankey.VendorName> VendorName = new ArrayList<com.ss.nitro.analytics.assetlc.domain.sankey.VendorName>();

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
	 * @return The VendorName
	 */
	public List<com.ss.nitro.analytics.assetlc.domain.sankey.VendorName> getVendorName() {
		return VendorName;
	}

	/**
	 * 
	 * @param VendorName
	 *            The VendorName
	 */
	public void setVendorName(
			List<com.ss.nitro.analytics.assetlc.domain.sankey.VendorName> VendorName) {
		this.VendorName = VendorName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "DeviceName [name=" + name + ", value=" + value
				+ ", VendorName=" + VendorName + "]";
	}

}
