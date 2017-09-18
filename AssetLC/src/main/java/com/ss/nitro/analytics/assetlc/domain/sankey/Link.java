package com.ss.nitro.analytics.assetlc.domain.sankey;

import java.util.ArrayList;
import java.util.List;

public class Link {

	private List<com.ss.nitro.analytics.assetlc.domain.sankey.Source> Source = new ArrayList<com.ss.nitro.analytics.assetlc.domain.sankey.Source>();

	/**
	 * 
	 * @return The Source
	 */
	public List<com.ss.nitro.analytics.assetlc.domain.sankey.Source> getSource() {
		return Source;
	}

	/**
	 * 
	 * @param Source
	 *            The Source
	 */
	public void setSource(
			List<com.ss.nitro.analytics.assetlc.domain.sankey.Source> Source) {
		this.Source = Source;
	}

}
