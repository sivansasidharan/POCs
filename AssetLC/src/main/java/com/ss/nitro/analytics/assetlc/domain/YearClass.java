package com.ss.nitro.analytics.assetlc.domain;

import java.math.BigDecimal;
import java.util.List;

public class YearClass {

	private List<ServiceLineElement> innerList;
	private BigDecimal totalAssetValue;
	private BigDecimal totalAssetCount;
	private BigDecimal poAmount;
	

	public List<ServiceLineElement> getInnerList() {
		return innerList;
	}

	public void setInnerList(List<ServiceLineElement> innerList) {
		this.innerList = innerList;
	}

	public BigDecimal getTotalAssetValue() {
		return totalAssetValue;
	}

	public void setTotalAssetValue(BigDecimal totalAssetValue) {
		this.totalAssetValue = totalAssetValue;
	}

	public BigDecimal getTotalAssetCount() {
		return totalAssetCount;
	}

	public void setTotalAssetCount(BigDecimal totalAssetCount) {
		this.totalAssetCount = totalAssetCount;
	}

	public BigDecimal getPoAmount() {
		return poAmount;
	}

	public void setPoAmount(BigDecimal poAmount) {
		this.poAmount = poAmount;
	}

}
