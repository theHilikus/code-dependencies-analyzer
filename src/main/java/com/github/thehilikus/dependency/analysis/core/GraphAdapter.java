package com.github.thehilikus.dependency.analysis.core;

import com.github.thehilikus.dependency.analysis.api.Graph;
import com.github.thehilikus.dependency.analysis.api.GraphOperation;

/**
 * An adapter between the internal jgraphT and ours
 *
 * @author hilikus
 */
public class GraphAdapter implements Graph {

    private final String name;
    private final org.jgrapht.Graph<?, ?> jgraphT;

    /**
     * @param name a unique name for the graph
     * @param jgraphT the graph backing up the adapter
     */
    public GraphAdapter(String name, org.jgrapht.Graph<?, ?> jgraphT) {
	this.name = name;
	this.jgraphT = jgraphT;
    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public int getNodesCount() {
	return jgraphT.vertexSet().size();
    }

    @Override
    public Graph apply(GraphOperation command) {
	// TODO Auto-generated method stub
	return null;
    }

}
