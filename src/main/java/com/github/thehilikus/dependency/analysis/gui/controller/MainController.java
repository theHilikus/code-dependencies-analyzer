package com.github.thehilikus.dependency.analysis.gui.controller;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.thehilikus.dependency.analysis.api.DependencySource;
import com.github.thehilikus.dependency.analysis.core.PackageGraphCreator;
import com.github.thehilikus.dependency.analysis.gui.MainPanel;
import com.github.thehilikus.dependency.analysis.gui.controller.tasks.GraphCreationTask;
import com.github.thehilikus.dependency.analysis.gui.controller.tasks.JavaSourceCodeSelectionTask;

/**
 * A controller of user events of MainPanel
 *
 * @author hilikus
 */
public class MainController {
    private MainPanel mainPanel;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private GraphCreationTask graphTask;

    private DependencySource sourceCodeReader;

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @FXML
    private void createPackageGraphSession() {
	if (sourceCodeReader == null) {
	    mainPanel.reportError("Please set a dependency provider first");
	    return;
	}

	cancelPreviousTask();

	try {
	    PackageGraphCreator packageGraph = new PackageGraphCreator();
	    graphTask = new GraphCreationTask("test", packageGraph, sourceCodeReader);

	    graphTask.setOnScheduled(event -> waiting(event));
	    graphTask.setOnSucceeded(event -> graphFinished(event));

	    executor.execute(graphTask);
	} catch (Exception exc) {
	    log.error("[createPackageGraphSession] There was a problem running a graph creation session: ", exc);
	    // TODO: add Alert dialog when openjfx 8u40 is released
	}

    }

    private Object graphFinished(WorkerStateEvent event) {
	mainPanel.waiting(false);

	return null;
    }

    private void cancelPreviousTask() {
	if (graphTask != null && !graphTask.isDone()) {
	    log.info("[createPackageGraphSession] Previous task was not finished. Cancelling it");
	    graphTask.cancel(true);
	}
    }

    @FXML
    private void selectJavaSourceCodeFolder() {

	DirectoryChooser folderChooser = new DirectoryChooser();
	folderChooser.setTitle("Select root folder to scan for java source code");
	File rootFolder = folderChooser.showDialog(mainPanel.getPrimaryStage());
	if (rootFolder != null) {
	    try {
		Task<DependencySource> codeSelectionTask = new JavaSourceCodeSelectionTask(rootFolder.toPath());

		codeSelectionTask.setOnScheduled(event -> waiting(event));
		codeSelectionTask.setOnSucceeded(event -> codeSelectionFinished(event));

		executor.execute(codeSelectionTask);
	    } catch (Exception exc) {
		log.error("[selectJavaSourceCodeFolder] There was a problem reading source code: ", exc);
		// TODO: add Alert dialog when openjfx 8u40 is released
	    }
	}
    }

    private Void codeSelectionFinished(WorkerStateEvent event) {
	sourceCodeReader = (DependencySource) event.getSource().getValue();
	log.info("[selectJavaSourceCodeFolder] Java sourceCode reader initialized successfully");
	mainPanel.waiting(false);

	return null;
    }

    private Void waiting(WorkerStateEvent event) {
	log.debug("[waiting] Waiting for {}", event.getSource());
	mainPanel.waiting(true);

	return null;
    }

    /**
     * @param mainPanel the first panel to load
     */
    public void setMainPanel(MainPanel mainPanel) {
	this.mainPanel = mainPanel;
    }

    /**
     * Cleans up all the resources
     */
    public void cleanup() {
	stopExecutor();
    }

    private void stopExecutor() {
	executor.shutdown();
	try {
	    executor.awaitTermination(5, TimeUnit.SECONDS);
	} catch (InterruptedException exc) {
	    executor.shutdownNow();
	}
	executor.shutdownNow();
    }
}
