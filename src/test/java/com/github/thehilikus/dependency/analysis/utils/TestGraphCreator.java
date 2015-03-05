package com.github.thehilikus.dependency.analysis.utils;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * A helper class to create jgraphT's graphs
 *
 * @author hilikus
 */
public class TestGraphCreator {

    private TestGraphCreator() {
	// block construction
    }

    /**
     * Constructs a graph with several connected components. For simplicity, each component has 3
     * vertices and vertex 1 is connected to 2 and 3
     * 
     * @param clustersCount
     * @return the graph with the connected components
     */
    public static DirectedGraph<String, DefaultEdge> createClusteredGraph(int clustersCount) {
	DirectedGraph<String, DefaultEdge> result = new DefaultDirectedGraph<>(DefaultEdge.class);
	for (int pos = 0; pos < clustersCount; pos++) {
	    result.addVertex(pos + "_1");
	    result.addVertex(pos + "_2");
	    result.addVertex(pos + "_3");
	    result.addEdge(pos + "_1", pos + "_2");
	    result.addEdge(pos + "_1", pos + "_3");
	}

	return result;
    }
}
