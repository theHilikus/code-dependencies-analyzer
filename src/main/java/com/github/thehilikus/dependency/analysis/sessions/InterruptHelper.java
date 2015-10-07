package com.github.thehilikus.dependency.analysis.sessions;

/**
 * A simple class to check thread interruptions
 *
 * @author hilikus
 */
public class InterruptHelper {

    /**
     * @return true if the running thread has been interrupted
     */
    public boolean isCancelled() {
	return Thread.currentThread().isInterrupted();
    }

    /**
     * Throws an exception if the running thread has been interrupted
     * 
     * @throws InterruptedException if the running thread has been interrupted
     */
    public void throwExceptionIfInterrupted() throws InterruptedException {
	if (Thread.currentThread().isInterrupted()) {
	    throw new InterruptedException();
	}
    }

}
