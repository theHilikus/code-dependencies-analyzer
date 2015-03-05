package com.github.thehilikus.dependency.analysis.core;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.thehilikus.dependency.analysis.api.Graph;

/**
 * Tests PackageGraphCreator
 *
 * @author hilikus
 */
public class PackageGraphCreatorTest {

    private PackageGraphCreator testingUnit;
    private TestDependencyProvider provider;

    /**
     * Configures the test
     */
    @BeforeMethod
    public void setup() {
	testingUnit = new PackageGraphCreator();
	provider = new TestDependencyProvider();
    }

    /**
     * Tests constructing a valid graph
     */
    @Test
    public void testCreateValidGraph() {
	provider.addDependencies("a.foo", "b.foo", "c.foo", "d.foo");
	provider.addDependencies("b.bar", "f.bar", "c.bar");
	provider.addDependencies("e.foo", "f.foo", "c.bar2");
	provider.addDependencies("e.bar", "f.foo", "g.foo");
	String graphName = "test-graph";
	Graph actual = testingUnit.createGraph(graphName, provider.getDependencies());

	assertEquals(actual.getName(), graphName);
	assertEquals(actual.getNodesCount(), 7);
	
    }
}
