package com.ss.nitro.analytics.assetlc.domain.expired;

import java.util.List;

public class ExSankeyModJson {
	private List<ExSource> links;
	private List<ExNode> nodes;
	public List<ExSource> getLinks() {
		return links;
	}
	public void setLinks(List<ExSource> links) {
		this.links = links;
	}
	public List<ExNode> getNodes() {
		return nodes;
	}
	public void setNodes(List<ExNode> nodes) {
		this.nodes = nodes;
	}
	
}
