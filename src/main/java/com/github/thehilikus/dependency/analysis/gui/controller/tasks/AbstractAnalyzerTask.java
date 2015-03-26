package com.github.thehilikus.dependency.analysis.gui.controller.tasks;

import java.util.concurrent.Callable;

import javafx.concurrent.Task;

/**
 * A javafx generic Task with common UI integration
 * 
 * @param <T> the return type of the task
 * @author hilikus
 */
public abstract class AbstractAnalyzerTask<T> extends Task<T> {

    private final Callable<T> session;

    /**
     * Constructs a javafx task with an analyzer session
     * 
     * @param graphSession
     */
    public AbstractAnalyzerTask(Callable<T> graphSession) {
	session = graphSession;
    }

    /**
     * @return a message to display when the task finished successfully
     */
    protected abstract String getSuccessMessage();

    /**
     * @return a message to display when the task finished unsuccessfully
     */
    protected abstract String getFailureMessage();

    /**
     * @return a message to display while the task is executing
     */
    protected String getRunningMessage() {
	return "Running task. Please wait...";
    }
    
    /**
     * @return a message to display when the task has been cancelled
     */
    protected String getCancelledMessage() {
	return "Task cancelled";
    }

    @Override
    protected void succeeded() {
	updateMessage(getSuccessMessage());
    }

    @Override
    protected void failed() {
	updateMessage(getFailureMessage());
    }

    @Override
    protected void running() {
	updateMessage(getRunningMessage());
    }

    @Override
    protected T call() throws Exception {
	try {
	    return session.call();
	} catch (InterruptedException exc) {
	    if (isCancelled()) {
		updateMessage(getCancelledMessage());
	    }
	    throw exc;
	}
    }

}
