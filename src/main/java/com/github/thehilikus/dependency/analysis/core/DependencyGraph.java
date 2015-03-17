package com.github.thehilikus.dependency.analysis.core;

import java.util.Collection;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import com.github.thehilikus.dependency.analysis.api.Graph;
import com.github.thehilikus.dependency.analysis.api.GraphOperation;

/**
 * A simple graph supported by jgraphT's graph
 *
 * @author hilikus
 */
public class DependencyGraph implements Graph, DirectedGraph<String, DefaultEdge> {

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

    @Override
    public Set<DefaultEdge> getAllEdges(String sourceVertex, String targetVertex) {
	return backend.getAllEdges(sourceVertex, targetVertex);
    }

    @Override
    public DefaultEdge getEdge(String sourceVertex, String targetVertex) {
	return backend.getEdge(sourceVertex, targetVertex);
    }

    @Override
    public EdgeFactory<String, DefaultEdge> getEdgeFactory() {
	return backend.getEdgeFactory();
    }

    @Override
    public DefaultEdge addEdge(String sourceVertex, String targetVertex) {
	return backend.addEdge(sourceVertex, targetVertex);
    }

    @Override
    public boolean addEdge(String sourceVertex, String targetVertex, DefaultEdge e) {
	return backend.addEdge(sourceVertex, targetVertex, e);
    }

    @Override
    public boolean addVertex(String v) {
	return backend.addVertex(v);
    }

    @Override
    public boolean containsEdge(String sourceVertex, String targetVertex) {
	return backend.containsEdge(sourceVertex, targetVertex);
    }

    @Override
    public boolean containsEdge(DefaultEdge e) {
	return backend.containsEdge(e);
    }

    @Override
    public boolean containsVertex(String v) {
	return backend.containsVertex(v);
    }

    @Override
    public Set<DefaultEdge> edgeSet() {
	return backend.edgeSet();
    }

    @Override
    public Set<DefaultEdge> edgesOf(String vertex) {
	return backend.edgesOf(vertex);
    }

    @Override
    public boolean removeAllEdges(Collection<? extends DefaultEdge> edges) {
	return backend.removeAllEdges(edges);
    }

    @Override
    public Set<DefaultEdge> removeAllEdges(String sourceVertex, String targetVertex) {
	return backend.removeAllEdges(sourceVertex, targetVertex);
    }

    @Override
    public boolean removeAllVertices(Collection<? extends String> vertices) {
	return backend.removeAllVertices(vertices);
    }

    @Override
    public DefaultEdge removeEdge(String sourceVertex, String targetVertex) {
	return backend.removeEdge(sourceVertex, targetVertex);
    }

    @Override
    public boolean removeEdge(DefaultEdge e) {
	return backend.removeEdge(e);
    }

    @Override
    public boolean removeVertex(String v) {
	return backend.removeVertex(v);
    }

    @Override
    public Set<String> vertexSet() {
	
	return backend.vertexSet();
    }

    @Override
    public String getEdgeSource(DefaultEdge e) {
	return backend.getEdgeSource(e);
    }

    @Override
    public String getEdgeTarget(DefaultEdge e) {
	return backend.getEdgeTarget(e);
    }

    @Override
    public double getEdgeWeight(DefaultEdge e) {
	return backend.getEdgeWeight(e);
    }

    @Override
    public int inDegreeOf(String vertex) {
	return backend.inDegreeOf(vertex);
    }

    @Override
    public Set<DefaultEdge> incomingEdgesOf(String vertex) {
	return backend.incomingEdgesOf(vertex);
    }

    @Override
    public int outDegreeOf(String vertex) {
	return backend.outDegreeOf(vertex);
    }

    @Override
    public Set<DefaultEdge> outgoingEdgesOf(String vertex) {
	return backend.outgoingEdgesOf(vertex);
    }

}
