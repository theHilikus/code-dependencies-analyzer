package com.github.thehilikus.dependency.analysis.core;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import com.github.thehilikus.dependency.analysis.api.Graph;
import com.github.thehilikus.dependency.analysis.api.GraphOperation;

/**
 * A simple graph supported by jgraphT's graph
 *
 * @author hilikus
 */
public class DependencyGraph implements Graph {

    private String name;

    private DirectedGraph<String, DefaultEdge> backend;

    /**
     * @param name the name of the graph
     */
    public DependencyGraph(String name) {
	this(name, new DefaultDirectedGraph<>(DefaultEdge.class));
    }

    DependencyGraph(String name, DirectedGraph<String, DefaultEdge> backend) {
	this.name = name;
	this.backend = backend;
    }

    /**
     * @return a unique identifier for this graph
     */
    @Override
    public String getName() {
	return name;
    }

    /**
     * @return the number of total nodes in the graph
     */
    @Override
    public int getNodesCount() {
	return backend.vertexSet().size();
    }

    /**
     * Executes an operation on the graph
     * 
     * @param operation the command to execute
     * @return the resulting graph
     */
    @Override
    public Graph apply(GraphOperation operation) {
	return operation.execute(name, backend);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "DependencyGraph [name=" + name + ", backend=" + backend + "]";
    }

}
