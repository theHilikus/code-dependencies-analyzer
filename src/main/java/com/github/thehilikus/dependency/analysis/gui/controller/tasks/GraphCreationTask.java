package com.github.thehilikus.dependency.analysis.gui.controller.tasks;

import java.util.Set;

import com.github.thehilikus.dependency.analysis.api.DependencySource;
import com.github.thehilikus.dependency.analysis.api.Graph;
import com.github.thehilikus.dependency.analysis.api.GraphCreator;
import com.github.thehilikus.dependency.analysis.sessions.GraphCreationSession;

import javafx.concurrent.Task;

/**
 * A javafx wrapper on GraphCreationSession
 *
 * @author hilikus
 */
public class GraphCreationTask extends Task<Graph> {

    private final GraphCreationSession session;

    /**
     * @param creationSession session to wrap
     */
    public GraphCreationTask(GraphCreationSession creationSession) {
	session = creationSession;
    }

    /**
     * @param name the unique name to give to the graph
     * @param creator the object used to construct the graph
     * @param sources the dependency sources
     */
    public GraphCreationTask(String name, GraphCreator creator, Set<DependencySource> sources) {
	this(new GraphCreationSession(name, creator, sources));
    }

    /**
     * @param name the unique name to give to the graph
     * @param creator the object used to construct the graph
     * @param source the dependency source
     */
    public GraphCreationTask(String name, GraphCreator creator, DependencySource source) {
	this(new GraphCreationSession(name, creator, source));
    }

    @Override
    protected Graph call() throws Exception {
	return session.call();
    }

}
