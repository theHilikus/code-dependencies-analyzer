package com.github.thehilikus.dependency.analysis.sessions;

import java.util.concurrent.Callable;

import com.github.thehilikus.dependency.analysis.api.Graph;
import com.github.thehilikus.dependency.analysis.api.GraphOperation;

/**
 * A unit of work where an operation is applied to a graph
 *
 * @author hilikus
 */
public class GraphOperationSession implements Callable<Graph> {

    private GraphOperation command;
    private Graph graph;

    /**
     * @param graph the graph to act on
     * @param command the operation to apply to the graph
     */
    public GraphOperationSession(Graph graph, GraphOperation command) {
	this.graph = graph;
	this.command = command;
    }

    @Override
    public Graph call() throws Exception {
	return graph.apply(command);
    }

}
