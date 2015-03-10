package com.github.thehilikus.dependency.analysis.gui.controller.tasks;

import java.nio.file.Path;

import com.github.thehilikus.dependency.analysis.api.DependencySource;
import com.github.thehilikus.dependency.analysis.readers.java.JavaSourceCodeReader;

/**
 * A task to select the source of java source dependencies
 *
 * @author hilikus
 */
public class JavaSourceCodeSelectionTask extends AbstractAnalyzerTask<DependencySource> {
    private Path rootFolder;

    /**
     * @param rootFolder the top folder containing the java sources
     */
    public JavaSourceCodeSelectionTask(Path rootFolder) {
	this.rootFolder = rootFolder;
    }

    @Override
    protected DependencySource call() throws Exception {
	DependencySource result = new JavaSourceCodeReader(rootFolder);
	return result;
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
