package com.github.thehilikus.dependency.analysis.utils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.thehilikus.dependency.analysis.api.DependencySource;

/**
 * A test class to mock all the files and their dependencies
 *
 * @author hilikus
 */
public class TestDependencyProvider {

    private Collection<DependencySource> sources = new ArrayList<>();

    /**
     * @param source the origin of the dependencies
     * @param destinations all the dependencies to add
     * @throws InterruptedException 
     */
    public void addDependencies(String source, String... destinations) throws InterruptedException {
	Map<String, Set<String>> deps = new HashMap<>();
	deps.put(source, Arrays.stream(destinations).collect(Collectors.toSet()));
	DependencySource mockSource = mock(DependencySource.class);
	when(mockSource.getDependencies()).thenReturn(deps);

	sources.add(mockSource);

    }

    /**
     * @return all the dependency structure created
     */
    public Collection<DependencySource> getDependencies() {
	return sources;
    }

}
