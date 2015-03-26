package com.github.thehilikus.dependency.analysis.gui.controller.tasks;

import java.nio.file.Path;
import java.util.concurrent.Callable;

import com.github.thehilikus.dependency.analysis.api.DependencySource;
import com.github.thehilikus.dependency.analysis.readers.java.JavaSourceCodeReader;

/**
 * A task to select the source of java source dependencies
 *
 * @author hilikus
 */
public class JavaSourceCodeSelectionTask extends AbstractAnalyzerTask<DependencySource> {

    /**
     * @param rootDirectory the root folder to scan for source code
     */
    public JavaSourceCodeSelectionTask(Path rootDirectory) {
	this(() -> new JavaSourceCodeReader(rootDirectory)); // no need for an explicit session here
    }

    /**
     * @param session the source scanning task
     */
    public JavaSourceCodeSelectionTask(Callable<DependencySource> session) {
	super(session);
    }

    @Override
    protected String getSuccessMessage() {
	return "Java sourceCode reader initialized successfully";
    }

    @Override
    protected String getFailureMessage() {
	return "Java sourceCode reader creation failed";
    }

}
