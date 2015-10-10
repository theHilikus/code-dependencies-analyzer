package com.github.thehilikus.dependency.analysis.core;

import java.util.List;
import java.util.stream.Collectors;

import com.github.thehilikus.dependency.analysis.api.Graph;
import com.github.thehilikus.dependency.analysis.api.GraphOperation;

/**
 * A graph that contains other graphs
 *
 * @author hilikus
 */
public class SuperGraph implements Graph {

    private List<? extends Graph> subgraphs;
    private String name;

    /**
     * @param name the name of the graph
     * @param subgraphs the list of inner graphs
     */
    public SuperGraph(String name, List<? extends Graph> subgraphs) {
	this.name = name;
	this.subgraphs = subgraphs;
    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public int getNodesCount() {
	return subgraphs.stream().mapToInt(subgraph -> subgraph.getNodesCount()).sum();
    }

    @Override
    public Graph apply(GraphOperation operation) {
	List<Graph> outputs = subgraphs.stream().map(subgraph -> subgraph.apply(operation))
		.collect(Collectors.toList());

	return new SuperGraph(name + '+' + operation.getName(), outputs);
    }

    /**
     * @return the list of inner graphs
     */
    public List<? extends Graph> getSubgraphs() {
	return subgraphs;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "SuperGraph [name=" + name + ", graphsCount=" + subgraphs.size() + "]";
    }

}
