package com.ss.nitro.analytics.assetlc.domain.expired;

import java.util.ArrayList;
import java.util.List;

public class ExSankeyNew {

	private List<ExLink> links = new ArrayList<ExLink>();
	private List<ExNode> nodes = new ArrayList<ExNode>();

	/**
	 * 
	 * @return The links
	 */
	public List<ExLink> getLinks() {
		return links;
	}

	/**
	 * 
	 * @param links
	 *            The links
	 */
	public void setLinks(List<ExLink> links) {
		this.links = links;
	}

	/**
	 * 
	 * @return The nodes
	 */
	public List<ExNode> getNodes() {
		return nodes;
	}

	/**
	 * 
	 * @param nodes
	 *            The nodes
	 */
	public void setNodes(List<ExNode> nodes) {
		this.nodes = nodes;
	}

}
