package com.github.thehilikus.dependency.analysis.core;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import com.github.thehilikus.dependency.analysis.api.DependencySource;
import com.github.thehilikus.dependency.analysis.api.Graph;
import com.github.thehilikus.dependency.analysis.api.GraphCreator;

/**
 * Creates a dependency graph where the vertices are java packages
 *
 * @author hilikus
 */
public class PackageGraphCreator implements GraphCreator {

    @Override
    public Graph createGraph(String name, Collection<DependencySource> sources) {
	DefaultDirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
	for (DependencySource depProvider : sources) {
	    Map<String, Set<String>> dependencies = depProvider.getDependencies();
	    for (Entry<String, Set<String>> depEntry : dependencies.entrySet()) {
		String sourcePackage = getPackageFromClass(depEntry.getKey());
		graph.addVertex(sourcePackage);
		for (String destinationClass : depEntry.getValue()) {
		    String destinationPackage = getPackageFromClass(destinationClass);
		    graph.addVertex(destinationPackage);
		    graph.addEdge(sourcePackage, destinationPackage);
		}
	    }
	}
	return new GraphAdapter(name, graph);
    }

    private static String getPackageFromClass(String className) {
	int dotPos = className.lastIndexOf('.');
	assert dotPos > 0 : "Invalid Fully-qualified class: " + className;

	return className.substring(0, dotPos);
    }
}
