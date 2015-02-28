package com.github.thehilikus.dependency.analysis.api;

import java.util.Set;

/**
 * A provider of dependencies
 *
 * @author hilikus
 */
public interface DependencySource {

    /**
     * @return all the valid dependencies in the source
     */
    Set<Dependency> getDependencies();

    /**
     * @return a unique identifier for this source
     */
    String getName();
}
