package com.ss.nitro.analytics.assetlc.domain.sankey;

import java.util.ArrayList;
import java.util.List;

public class SankeyNew {

	private List<Link> links = new ArrayList<Link>();
	private List<Node> nodes = new ArrayList<Node>();

	/**
	 * 
	 * @return The links
	 */
	public List<Link> getLinks() {
		return links;
	}

	/**
	 * 
	 * @param links
	 *            The links
	 */
	public void setLinks(List<Link> links) {
		this.links = links;
	}

	/**
	 * 
	 * @return The nodes
	 */
	public List<Node> getNodes() {
		return nodes;
	}

	/**
	 * 
	 * @param nodes
	 *            The nodes
	 */
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

}
