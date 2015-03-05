package com.github.thehilikus.dependency.analysis.operations;

import static org.testng.Assert.assertEquals;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.thehilikus.dependency.analysis.api.Graph;
import com.github.thehilikus.dependency.analysis.core.SuperGraph;
import com.github.thehilikus.dependency.analysis.utils.TestGraphCreator;

/**
 * Tests FindConnectedComponents
 *
 * @author hilikus
 */
public class FindConnectedComponentsTest {

    private FindConnectedComponents testingUnit;

    /**
     * Prepares the tests
     */
    @BeforeMethod
    public void setup() {
	testingUnit = new FindConnectedComponents();
    }

    /**
     * Tests splitting a graph with one connected component
     */
    @Test
    public void testExecuteSingleCluster() {
	testCluster(1);
    }

    /**
     * Tests splitting a graph with three connected components
     */
    @Test
    public void testExecuteManyClusters() {
	testCluster(3);
    }

    private void testCluster(int clusterCount) {
	DirectedGraph<String, DefaultEdge> graph = TestGraphCreator.createClusteredGraph(clusterCount);
	SuperGraph result = testingUnit.execute("testGraph", graph);
	assertEquals(result.getSubgraphs().size(), clusterCount);
	Graph cluster = result.getSubgraphs().get(0);
	assertEquals(cluster.getNodesCount(), 3);
	assertEquals(result.getNodesCount(), 3 * clusterCount);
    }

}
