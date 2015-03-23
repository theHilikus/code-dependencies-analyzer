package com.github.thehilikus.dependency.analysis.core;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import com.github.thehilikus.dependency.analysis.api.Graph;
import com.github.thehilikus.dependency.analysis.api.GraphOperation;

import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

/**
 * A simple graph supported by jgraphT's graph
 *
 * @author hilikus
 */
public class DependencyGraph implements Graph, edu.uci.ics.jung.graph.Graph<String, DefaultEdge> {

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
    public Collection<DefaultEdge> getEdges() {
	return backend.edgeSet();
    }

    @Override
    public Collection<String> getVertices() {
	return backend.vertexSet();
    }

    @Override
    public boolean containsVertex(String vertex) {
	return backend.containsVertex(vertex);
    }

    @Override
    public boolean containsEdge(DefaultEdge edge) {
	return backend.containsEdge(edge);
    }

    @Override
    public int getEdgeCount() {
	return backend.edgeSet().size();
    }

    @Override
    public int getVertexCount() {
	return backend.vertexSet().size();
    }

    @Override
    public Collection<String> getNeighbors(String vertex) {
	Set<DefaultEdge> edges = backend.edgesOf(vertex);
	return edges.stream().map(edge -> backend.getEdgeTarget(edge)).collect(Collectors.toSet());
	
    }

    @Override
    public Collection<DefaultEdge> getIncidentEdges(String vertex) {
	return backend.edgesOf(vertex);
    }

    @Override
    public Collection<String> getIncidentVertices(DefaultEdge edge) {
	throw new UnsupportedOperationException();
    }

    @Override
    public DefaultEdge findEdge(String v1, String v2) {
	return backend.getEdge(v1, v2);
    }

    @Override
    public Collection<DefaultEdge> findEdgeSet(String v1, String v2) {
	return backend.getAllEdges(v1, v2);
    }

    @Override
    public boolean addVertex(String vertex) {
	return backend.addVertex(vertex);
    }

    @Override
    public boolean addEdge(DefaultEdge edge, Collection<? extends String> vertices) {
	throw new UnsupportedOperationException();
    }

    @Override
    public boolean addEdge(DefaultEdge edge, Collection<? extends String> vertices, EdgeType edge_type) {
	throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeVertex(String vertex) {
	return backend.removeVertex(vertex);
    }

    @Override
    public boolean removeEdge(DefaultEdge edge) {
	return backend.removeEdge(edge);
    }

    @Override
    public boolean isNeighbor(String v1, String v2) {
	throw new UnsupportedOperationException();
    }

    @Override
    public boolean isIncident(String vertex, DefaultEdge edge) {
	throw new UnsupportedOperationException();
    }

    @Override
    public int degree(String vertex) {
	return backend.inDegreeOf(vertex) + backend.outDegreeOf(vertex);
    }

    @Override
    public int getNeighborCount(String vertex) {
	return backend.outDegreeOf(vertex);
    }

    @Override
    public int getIncidentCount(DefaultEdge edge) {
	return 2;
    }

    @Override
    public EdgeType getEdgeType(DefaultEdge edge) {
	return getDefaultEdgeType();
    }

    @Override
    public EdgeType getDefaultEdgeType() {
	return EdgeType.DIRECTED;
    }

    @Override
    public Collection<DefaultEdge> getEdges(EdgeType edge_type) {
	if (edge_type == EdgeType.DIRECTED) {
	    return getEdges();
	} else {
	    return null;
	}
	
    }

    @Override
    public int getEdgeCount(EdgeType edge_type) {
	return getEdges().size();
    }

    @Override
    public Collection<DefaultEdge> getInEdges(String vertex) {
	return backend.incomingEdgesOf(vertex);
    }

    @Override
    public Collection<DefaultEdge> getOutEdges(String vertex) {
	return backend.outgoingEdgesOf(vertex);
    }

    @Override
    public Collection<String> getPredecessors(String vertex) {
	throw new UnsupportedOperationException();
    }

    @Override
    public Collection<String> getSuccessors(String vertex) {
	Set<DefaultEdge> edges = backend.edgesOf(vertex);
	return edges.stream().map(edge -> backend.getEdgeTarget(edge)).collect(Collectors.toSet());
    }

    @Override
    public int inDegree(String vertex) {
	return backend.inDegreeOf(vertex);
    }

    @Override
    public int outDegree(String vertex) {
	return backend.outDegreeOf(vertex);
    }

    @Override
    public boolean isPredecessor(String v1, String v2) {
	return getPredecessors(v1).contains(v2);
    }

    @Override
    public boolean isSuccessor(String v1, String v2) {
	return getSuccessors(v1).contains(v2);
    }

    @Override
    public int getPredecessorCount(String vertex) {
	return getPredecessors(vertex).size();
    }

    @Override
    public int getSuccessorCount(String vertex) {
	return getSuccessors(vertex).size();
    }

    @Override
    public String getSource(DefaultEdge directed_edge) {
	return backend.getEdgeSource(directed_edge);
    }

    @Override
    public String getDest(DefaultEdge directed_edge) {
	return backend.getEdgeTarget(directed_edge);
    }

    @Override
    public boolean isSource(String vertex, DefaultEdge edge) {
	return getSource(edge).equals(vertex);
    }

    @Override
    public boolean isDest(String vertex, DefaultEdge edge) {
	return getDest(edge).equals(vertex);
    }

    @Override
    public boolean addEdge(DefaultEdge e, String v1, String v2) {
	return backend.addEdge(v1, v2, e);
    }

    @Override
    public boolean addEdge(DefaultEdge e, String v1, String v2, EdgeType edgeType) {
	return addEdge(e, v1, v2, EdgeType.DIRECTED);
    }

    @Override
    public Pair<String> getEndpoints(DefaultEdge edge) {
	return new Pair<>(getSource(edge), getDest(edge));
    }

    @Override
    public String getOpposite(String vertex, DefaultEdge edge) {
	if (getDest(edge).equals(vertex)) {
	    return getSource(edge);
	} else if (getSource(edge).equals(vertex)){
	    return getDest(edge);
	}
	throw new IllegalArgumentException("Vertex is not connected to edge");
    }

}
