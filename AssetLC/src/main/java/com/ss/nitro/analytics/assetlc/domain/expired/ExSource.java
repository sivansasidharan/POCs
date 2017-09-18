package com.ss.nitro.analytics.assetlc.domain.expired;

import java.util.ArrayList;
import java.util.List;

public class ExSource {

	private String name;
	private ExValue value;
	private List<com.ss.nitro.analytics.assetlc.domain.expired.ExDeviceName> DeviceName = new ArrayList<com.ss.nitro.analytics.assetlc.domain.expired.ExDeviceName>();

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
	 * @return The DeviceName
	 */
	public List<com.ss.nitro.analytics.assetlc.domain.expired.ExDeviceName> getDeviceName() {
		return DeviceName;
	}

	/**
	 * 
	 * @param DeviceName
	 *            The DeviceName
	 */
	public void setDeviceName(
			List<com.ss.nitro.analytics.assetlc.domain.expired.ExDeviceName> DeviceName) {
		this.DeviceName = DeviceName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Source [name=" + name + ", value=" + value + ", DeviceName="
				+ DeviceName + "]";
	}

}
