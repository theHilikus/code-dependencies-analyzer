package com.github.thehilikus.dependency.analysis.api;

/**
 * A command to apply to a graph
 *
 * @author hilikus
 */
public interface GraphOperation {

    /**
     * @return a unique identifier of the operation
     */
    String getName();

}
