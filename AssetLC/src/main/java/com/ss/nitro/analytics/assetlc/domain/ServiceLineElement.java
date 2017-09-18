package com.ss.nitro.analytics.assetlc.domain;

import java.math.BigDecimal;

public class ServiceLineElement {
	private String Department;
	private double totalAssetValue;
	private double totalAssetCount;
	private double poAmount;


	public BigDecimal getTotalAssetValue() {
		return BigDecimal.valueOf(totalAssetValue);
	}

	public void setTotalAssetValue(double totalAssetValue) {
		this.totalAssetValue = totalAssetValue;
	}

	public BigDecimal getTotalAssetCount() {
		return BigDecimal.valueOf(totalAssetCount);
	}

	public void setTotalAssetCount(double totalAssetCount) {
		this.totalAssetCount = totalAssetCount;
	}

	public BigDecimal getPoAmount() {
		return BigDecimal.valueOf(poAmount);
	}

	public void setPoAmount(double poAmount) {
		this.poAmount = poAmount;
	}

	public String getDepartment() {
		return Department;
	}

	public void setDepartment(String department) {
		Department = department;
	}


}
