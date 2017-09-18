package com.ss.nitro.analytics.assetlc.domain.expired;

import java.util.ArrayList;
import java.util.List;

public class ExDeviceName {

	private String name;
	private ExValue value;
	private List<com.ss.nitro.analytics.assetlc.domain.expired.ExVendorName> VendorName = new ArrayList<com.ss.nitro.analytics.assetlc.domain.expired.ExVendorName>();

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
	 * @return The VendorName
	 */
	public List<com.ss.nitro.analytics.assetlc.domain.expired.ExVendorName> getVendorName() {
		return VendorName;
	}

	/**
	 * 
	 * @param VendorName
	 *            The VendorName
	 */
	public void setVendorName(
			List<com.ss.nitro.analytics.assetlc.domain.expired.ExVendorName> VendorName) {
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
