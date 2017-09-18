package com.ss.nitro.analytics.assetlc.domain.expired;

import java.util.ArrayList;
import java.util.List;

public class ExLink {

	private List<com.ss.nitro.analytics.assetlc.domain.expired.ExSource> Source = new ArrayList<com.ss.nitro.analytics.assetlc.domain.expired.ExSource>();

	/**
	 * 
	 * @return The Source
	 */
	public List<com.ss.nitro.analytics.assetlc.domain.expired.ExSource> getSource() {
		return Source;
	}

	/**
	 * 
	 * @param Source
	 *            The Source
	 */
	public void setSource(
			List<com.ss.nitro.analytics.assetlc.domain.expired.ExSource> Source) {
		this.Source = Source;
	}

}
