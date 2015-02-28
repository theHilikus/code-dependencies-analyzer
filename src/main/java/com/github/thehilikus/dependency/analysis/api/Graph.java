package com.github.thehilikus.dependency.analysis.api;

/**
 * A set of interconnected, directed nodes
 *
 * @author hilikus
 */
public interface Graph {

    /**
     * @return a unique identifier for this graph
     */
    String getName();

    /**
     * @return the number of total nodes in the graph
     */
    int getNodesCount();

    /**
     * @param command the operation to apply to the graph
     * @return a resulting graph after applying the operation
     */
    Graph apply(GraphOperation command);
}
