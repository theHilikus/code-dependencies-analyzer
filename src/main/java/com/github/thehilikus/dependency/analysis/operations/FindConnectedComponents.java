package com.github.thehilikus.dependency.analysis.operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;

import com.github.thehilikus.dependency.analysis.api.GraphOperation;
import com.github.thehilikus.dependency.analysis.core.InnerGraphFactory;
import com.github.thehilikus.dependency.analysis.core.SuperGraph;

/**
 * Creates a supergraph containing the connected components of the original graph
 *
 * @author hilikus
 */
public class FindConnectedComponents implements GraphOperation {

    @Override
    public String getName() {
	return "ConnectedComponentFinder";
    }

    @Override
    public String getDescription() {
	return "Finds the Connected Components in the graph";
    }

    @Override
    public SuperGraph execute(String name, DirectedGraph<String, DefaultEdge> graph) {
	ConnectivityInspector<String, DefaultEdge> connectivity = new ConnectivityInspector<>(graph);
	List<Map<String, Set<String>>> edgesList = createPartitionedEdges(graph, connectivity.connectedSets());

	return InnerGraphFactory.createSupergraph(name + "+" + getName(), edgesList);
    }

    private static List<Map<String, Set<String>>> createPartitionedEdges(DirectedGraph<String, DefaultEdge> graph,
	    List<Set<String>> connectedSets) {
	List<Map<String, Set<String>>> result = new ArrayList<>();
	for (Set<String> set : connectedSets) {
	    Map<String, Set<String>> edges = new HashMap<>();
	    for (String origin : set) {
		Set<DefaultEdge> targets = graph.outgoingEdgesOf(origin);
		Set<String> targetStrings = targets.stream().map(edge -> graph.getEdgeTarget(edge))
			.collect(Collectors.toSet());
		if (!targetStrings.isEmpty()) {
		    edges.put(origin, targetStrings);
		}
	    }
	    result.add(edges);
	}
	return result;
    }
}
