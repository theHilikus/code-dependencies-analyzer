package com.github.thehilikus.dependency.analysis.core;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import com.github.thehilikus.dependency.analysis.api.Graph;

/**
 * A helper class to construct graphs
 *
 * @author hilikus
 */
public class InnerGraphFactory {

    private InnerGraphFactory() {
	// blocked
    }

    /**
     * Creates a supergraph
     * 
     * @param name the name of the new graph
     * @param listOfEdges a list of edges of each subgraph
     * @return a super graph composed of the given elements
     */
    public static SuperGraph createSupergraph(String name, List<Map<String, Set<String>>> listOfEdges) {
	List<Graph> subgraphs = listOfEdges.stream().map(edges -> createGraph(name + "_sub", edges))
		.collect(Collectors.toList());
	return new SuperGraph(name, subgraphs);
    }

    /**
     * Constructs a simple graph
     * 
     * @param name the name of the new graph
     * @param edges a list of edges
     * @return a graph composed of the given elements
     */
    public static Graph createGraph(String name, Map<String, Set<String>> edges) {
	DefaultDirectedGraph<String, DefaultEdge> innerGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
	for (Entry<String, Set<String>> depEntry : edges.entrySet()) {
	    String source = depEntry.getKey();
	    innerGraph.addVertex(source);
	    for (String destination : depEntry.getValue()) {
		innerGraph.addVertex(destination);
		innerGraph.addEdge(source, destination);
	    }
	}

	return new DependencyGraph(name, innerGraph);
    }
}