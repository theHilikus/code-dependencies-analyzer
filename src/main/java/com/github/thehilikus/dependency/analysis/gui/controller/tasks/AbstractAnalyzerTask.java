package com.github.thehilikus.dependency.analysis.gui.controller.tasks;

import javafx.concurrent.Task;

/**
 * A javafx generic Task with common UI integration
 * 
 * @param <T> the return type of the task
 * @author hilikus
 */
public abstract class AbstractAnalyzerTask<T> extends Task<T> {

    /**
     * @return a message to display when the task finished successfully
     */
    protected abstract String getSuccessMessage();

    /**
     * @return a message to display when the task finished unsuccessfully
     */
    protected abstract String getFailureMessage();

    @Override
    protected void succeeded() {
	updateMessage(getSuccessMessage());
    }

    @Override
    protected void failed() {
	updateMessage(getFailureMessage());
    }
    
}
