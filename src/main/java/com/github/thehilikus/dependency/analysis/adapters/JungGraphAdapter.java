package com.github.thehilikus.dependency.analysis.adapters;

import java.util.Collection;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class JungGraphAdapter implements Graph<String, String>{

    public JungGraphAdapter(com.github.thehilikus.dependency.analysis.api.Graph graph) {
	
    }

    @Override
    public Collection<String> getEdges() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<String> getVertices() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean containsVertex(String vertex) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean containsEdge(String edge) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public int getEdgeCount() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int getVertexCount() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public Collection<String> getNeighbors(String vertex) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<String> getIncidentEdges(String vertex) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<String> getIncidentVertices(String edge) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String findEdge(String v1, String v2) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<String> findEdgeSet(String v1, String v2) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean addVertex(String vertex) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean addEdge(String edge, Collection<? extends String> vertices) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean addEdge(String edge, Collection<? extends String> vertices, EdgeType edge_type) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean removeVertex(String vertex) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean removeEdge(String edge) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isNeighbor(String v1, String v2) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isIncident(String vertex, String edge) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public int degree(String vertex) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int getNeighborCount(String vertex) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int getIncidentCount(String edge) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public EdgeType getEdgeType(String edge) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public EdgeType getDefaultEdgeType() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<String> getEdges(EdgeType edge_type) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public int getEdgeCount(EdgeType edge_type) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public Collection<String> getInEdges(String vertex) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<String> getOutEdges(String vertex) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<String> getPredecessors(String vertex) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<String> getSuccessors(String vertex) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public int inDegree(String vertex) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int outDegree(String vertex) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public boolean isPredecessor(String v1, String v2) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isSuccessor(String v1, String v2) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public int getPredecessorCount(String vertex) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int getSuccessorCount(String vertex) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public String getSource(String directed_edge) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String getDest(String directed_edge) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean isSource(String vertex, String edge) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isDest(String vertex, String edge) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean addEdge(String e, String v1, String v2) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean addEdge(String e, String v1, String v2, EdgeType edgeType) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public Pair<String> getEndpoints(String edge) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String getOpposite(String vertex, String edge) {
	// TODO Auto-generated method stub
	return null;
    }
    
}
