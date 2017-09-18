package com.ss.nitro.analytics.assetlc.domain;

import java.util.List;

public class SankeyJson {
	private List<Sankey> links;
	private List<Nodes> nodes;
	
	public List<Sankey> getLinks() {
		return links;
	}
	public void setLinks(List<Sankey> links) {
		this.links = links;
	}
	public List<Nodes> getNodes() {
		return nodes;
	}
	public void setNodes(List<Nodes> nodes) {
		this.nodes = nodes;
	}
	

}
