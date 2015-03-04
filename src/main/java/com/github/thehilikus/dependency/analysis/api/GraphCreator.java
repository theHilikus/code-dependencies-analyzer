package com.github.thehilikus.dependency.analysis.api;

import java.util.Collection;

/**
 * An entity capable of constructing a graph out of a group of dependencies
 *
 * @author hilikus
 */
public interface GraphCreator {

    /**
     * @param graphName the unique name of the graph
     * @param sources all the dependency sources to use to construct the graph
     * @return the constructed graph
     */
    Graph createGraph(String graphName, Collection<DependencySource> sources);
}
