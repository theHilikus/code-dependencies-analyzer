package com.github.thehilikus.dependency.analysis.gui.controller.tasks;

import com.github.thehilikus.dependency.analysis.api.Graph;
import com.github.thehilikus.dependency.analysis.api.GraphOperation;
import com.github.thehilikus.dependency.analysis.sessions.GraphOperationSession;

/**
 * A javafx wrapper on GraphOperationSession
 *
 * @author hilikus
 */
public class GraphOperationTask extends AbstractAnalyzerTask<Graph> {

    /**
     * @param operationSession session to wrap
     */
    public GraphOperationTask(GraphOperationSession operationSession) {
	super(operationSession);
    }

    /**
     * @param graph the graph to operate on
     * @param command the command to apply to the graph
     */
    public GraphOperationTask(Graph graph, GraphOperation command) {
	this(new GraphOperationSession(graph, command));
    }

    @Override
    protected String getSuccessMessage() {
	return "Operation executed successfully";
    }

    @Override
    protected String getFailureMessage() {
	return "Operation failed";
    }

}
