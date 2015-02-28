package com.github.thehilikus.dependency.analysis.sessions;

import java.util.Set;

import com.github.thehilikus.dependency.analysis.api.DependencySource;
import com.github.thehilikus.dependency.analysis.api.GraphCreator;

/**
 * A unit of work where a graph is created
 *
 * @author hilikus
 */
public class GraphCreationSession implements Runnable {

    private GraphCreator graphCreator;
    private Set<DependencySource> dependenciesSources;

    /**
     * @param creator the object used to construct the graph
     * @param sources the group of dependency sources
     */
    public GraphCreationSession(GraphCreator creator, Set<DependencySource> sources) {
	graphCreator = creator;
	dependenciesSources = sources;
    }

    @Override
    public void run() {
	graphCreator.createGraph(dependenciesSources);
    }

}
