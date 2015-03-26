package com.github.thehilikus.dependency.analysis.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.thehilikus.dependency.analysis.api.DependencySource;
import com.github.thehilikus.dependency.analysis.api.Graph;
import com.github.thehilikus.dependency.analysis.api.GraphCreator;
import com.github.thehilikus.dependency.analysis.sessions.InterruptHelper;

/**
 * Creates a dependency graph where the vertices are java packages
 *
 * @author hilikus
 */
public class PackageGraphCreator implements GraphCreator {

    @Override
    public Graph createGraph(String name, Collection<DependencySource> sources) throws InterruptedException {
	InterruptHelper.throwExceptionIfInterrupted();

	Map<String, Set<String>> consolidated = new HashMap<>();
	for (DependencySource depProvider : sources) {
	    InterruptHelper.throwExceptionIfInterrupted();
	    Map<String, Set<String>> dependenciesWithClasses = depProvider.getDependencies();
	    Map<String, Set<String>> dependencies = removeClassesNames(dependenciesWithClasses);
	    consolidateDependencies(consolidated, dependencies);
	}
	return InnerGraphFactory.createGraph(name, consolidated);
    }

    private static Map<String, Set<String>> removeClassesNames(Map<String, Set<String>> dependenciesWithClasses)
	    throws InterruptedException {
	Map<String, Set<String>> result = new HashMap<>();
	for (Entry<String, Set<String>> entry : dependenciesWithClasses.entrySet()) {
	    InterruptHelper.throwExceptionIfInterrupted();
	    String keyPackage = getPackageFromClass(entry.getKey());
	    Set<String> values = entry.getValue().stream().map(value -> getPackageFromClass(value))
		    .collect(Collectors.toSet());
	    if (result.containsKey(keyPackage)) {
		result.get(keyPackage).addAll(values);
	    } else {
		result.put(keyPackage, values);
	    }
	}
	return result;
    }

    private static String getPackageFromClass(String className) {
	int dotPos = className.lastIndexOf('.');
	assert dotPos > 0 : "Invalid Fully-qualified class: " + className;

	return className.substring(0, dotPos);
    }

    private static void consolidateDependencies(Map<String, Set<String>> consolidated, Map<String, Set<String>> dependencies)
	    throws InterruptedException {
	for (Entry<String, Set<String>> entry : dependencies.entrySet()) {
	    InterruptHelper.throwExceptionIfInterrupted();
	    String key = entry.getKey();
	    if (consolidated.containsKey(key)) {
		consolidated.get(key).addAll(entry.getValue());
	    } else {
		consolidated.put(key, entry.getValue());
	    }
	}

    }
}
