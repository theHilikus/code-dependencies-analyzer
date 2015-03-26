package com.github.thehilikus.dependency.analysis.sessions;

/**
 * A simple class to check thread interruptions
 *
 * @author hilikus
 */
public class InterruptHelper {

    private InterruptHelper() {
	// blocked
    }

    /**
     * @return true if the running thread has been interrupted
     */
    public static boolean isCancelled() {
	return Thread.currentThread().isInterrupted();
    }

    /**
     * Throws an exception if the running thread has been interrupted
     * 
     * @throws InterruptedException if the running thread has been interrupted
     */
    public static void throwExceptionIfInterrupted() throws InterruptedException {
	if (Thread.currentThread().isInterrupted()) {
	    throw new InterruptedException();
	}
    }

}
