package com.ss.nitro.analytics.assetlc.domain.expired;

import java.util.ArrayList;
import java.util.List;

public class ExBrand {

	private String name;

	private ExValue value;

	private List<com.ss.nitro.analytics.assetlc.domain.expired.ExAsset> assetName = new ArrayList<com.ss.nitro.analytics.assetlc.domain.expired.ExAsset>();

	public List<com.ss.nitro.analytics.assetlc.domain.expired.ExAsset> getAssetName() {
		return assetName;
	}

	public void setAssetName(
			List<com.ss.nitro.analytics.assetlc.domain.expired.ExAsset> assetName) {
		this.assetName = assetName;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Brand [name=" + name + ", value=" + value + "]";
	}

}
