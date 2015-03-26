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
     * @throws InterruptedException if the operation is cancelled mid-flight
     */
    Map<String, Set<String>> getDependencies() throws InterruptedException;

    /**
     * @return a unique identifier for this source
     */
    String getName();

    /**
     * @return a long, human-friendly explanation of the source
     */
    String getDescription();
}
