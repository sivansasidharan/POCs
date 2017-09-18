package com.ss.nitro.analytics.assetlc.domain.sankey;

import java.util.List;

import com.ss.nitro.analytics.assetlc.domain.Nodes;

public class SankeyModJson {
	private List<Source> links;
	private List<Nodes> nodes;
	public List<Source> getLinks() {
		return links;
	}
	public void setLinks(List<Source> links) {
		this.links = links;
	}
	public List<Nodes> getNodes() {
		return nodes;
	}
	public void setNodes(List<Nodes> nodes) {
		this.nodes = nodes;
	}
	
}
