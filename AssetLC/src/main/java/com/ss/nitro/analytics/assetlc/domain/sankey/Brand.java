package com.ss.nitro.analytics.assetlc.domain.sankey;

public class Brand {

	private String name;

	private Value value;

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
