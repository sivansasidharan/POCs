package com.ss.nitro.analytics.assetlc.domain.expired;

public class ExAsset {

	private String name;
	private ExValue value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ExValue getValue() {
		return value;
	}

	public void setValue(ExValue value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ExAsset [name=" + name + ", value=" + value + "]";
	}

}
