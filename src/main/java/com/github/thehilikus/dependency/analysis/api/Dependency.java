package com.github.thehilikus.dependency.analysis.api;

/**
 * An entry that represents a dependency to another entity
 *
 * @author hilikus
 */
public interface Dependency {
    /**
     * @return a unique identifier of this dependency
     */
    String getName();
}
