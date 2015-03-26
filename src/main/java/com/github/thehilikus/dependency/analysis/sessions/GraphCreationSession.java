package com.github.thehilikus.dependency.analysis.sessions;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import com.github.thehilikus.dependency.analysis.api.DependencySource;
import com.github.thehilikus.dependency.analysis.api.Graph;
import com.github.thehilikus.dependency.analysis.api.GraphCreator;

/**
 * A unit of work where a graph is created
 *
 * @author hilikus
 */
public class GraphCreationSession implements Callable<Graph> {

    private final GraphCreator graphCreator;
    private final Set<DependencySource> dependenciesSources;
    private final String graphName;

    /**
     * Creates a session with multiple dependency sources
     * 
     * @param name the unique name to give to the graph
     * @param creator the object used to construct the graph
     * @param sources the group of dependency sources
     */
    public GraphCreationSession(String name, GraphCreator creator, Set<DependencySource> sources) {
	graphName = name;
	graphCreator = creator;
	dependenciesSources = sources;
    }

    /**
     * Creates a session with a single dependency source
     * 
     * @param name the unique name to give to the graph
     * @param creator the object used to construct the graph
     * @param source the dependency source
     */
    public GraphCreationSession(String name, GraphCreator creator, DependencySource source) {
	this(name, creator, createSetWithElement(source));
    }

    private static Set<DependencySource> createSetWithElement(DependencySource source) {
	Set<DependencySource> result = new HashSet<>();
	result.add(source);

	return result;
    }

    @Override
    public Graph call() throws Exception {
	return graphCreator.createGraph(graphName, dependenciesSources);
    }
    
}
