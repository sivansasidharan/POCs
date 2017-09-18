package com.ss.nitro.analytics.assetlc.domain.sankey;

import java.util.ArrayList;
import java.util.List;

public class Source {

	private String name;
	private Value value;
	private List<com.ss.nitro.analytics.assetlc.domain.sankey.DeviceName> DeviceName = new ArrayList<com.ss.nitro.analytics.assetlc.domain.sankey.DeviceName>();

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
	 * @return The DeviceName
	 */
	public List<com.ss.nitro.analytics.assetlc.domain.sankey.DeviceName> getDeviceName() {
		return DeviceName;
	}

	/**
	 * 
	 * @param DeviceName
	 *            The DeviceName
	 */
	public void setDeviceName(
			List<com.ss.nitro.analytics.assetlc.domain.sankey.DeviceName> DeviceName) {
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
