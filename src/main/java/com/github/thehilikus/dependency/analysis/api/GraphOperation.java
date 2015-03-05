package com.github.thehilikus.dependency.analysis.api;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * A command to apply to a graph
 *
 * @author hilikus
 */
public interface GraphOperation {

    /**
     * @return the short name of the operation
     */
    String getName();

    /**
     * @return a longer definition of what the operation does
     */
    String getDescription();

    /**
     * Executes the operation on the given graph. This method is intended to be invoked by the Graph
     * implementation
     * 
     * @param sourceName the name of the graph on which the operation is acting
     * @param graph the internal graph to act on
     * @return a resulting graph after applying the operation
     */
    Graph execute(String sourceName, DirectedGraph<String, DefaultEdge> graph);

}
