package com.ss.nitro.analytics.assetlc.domain.sankey;


public class Value {

	private Integer level;
	private String value1;
	private String value2;

	/**
	 * 
	 * @return The level
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * 
	 * @param level
	 *            The level
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * 
	 * @return The value1
	 */
	public String getValue1() {
		return value1;
	}

	/**
	 * 
	 * @param value1
	 *            The value1
	 */
	public void setValue1(String value1) {
		this.value1 = value1;
	}

	/**
	 * 
	 * @return The value2
	 */
	public String getValue2() {
		return value2;
	}

	/**
	 * 
	 * @param value2
	 *            The value2
	 */
	public void setValue2(String value2) {
		this.value2 = value2;
	}

	@Override
	public String toString() {
		return "Value [level=" + level + ", value1=" + value1 + ", value2="
				+ value2 + "]";
	}

}
