package com.github.thehilikus.dependency.analysis.core;

import com.github.thehilikus.dependency.analysis.api.Dependency;

/**
 * A flat dependency
 *
 * @author hilikus
 */
public class BasicDependency implements Dependency {

    private String name;

    /**
     * @param name the unique name of the dependency
     */
    public BasicDependency(String name) {
	if (name == null || name.trim().isEmpty()) {
	    throw new IllegalArgumentException("Name cannot be empty");
	}
	this.name = name.trim();
    }

    @Override
    public String getName() {
	return name;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "BasicDependency <" + name + ">";
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	BasicDependency other = (BasicDependency) obj;
	if (name == null) {
	    if (other.name != null) {
		return false;
	    }
	} else if (!name.equals(other.name)) {
	    return false;
	}
	return true;
    }

}
