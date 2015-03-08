package com.github.thehilikus.dependency.analysis.api;

import java.util.Map;
import java.util.Set;

/**
 * A provider of multiple dependencies
 *
 * @author hilikus
 */
public interface DependencySource {

    /**
     * @return all the valid dependencies in the source. The key is the origin and the value is a
     *         set of dependents of the origin
     */
    Map<String, Set<String>> getDependencies();

    /**
     * @return a unique identifier for this source
     */
    String getName();
}
