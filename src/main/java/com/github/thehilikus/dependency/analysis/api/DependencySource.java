package com.github.thehilikus.dependency.analysis.api;

import java.util.Map;
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
    Map<String, Set<String>> getDependencies();

    /**
     * @return a unique identifier for this source
     */
    String getName();
}
