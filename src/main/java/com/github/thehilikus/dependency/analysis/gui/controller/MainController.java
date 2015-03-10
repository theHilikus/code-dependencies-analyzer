package com.github.thehilikus.dependency.analysis.gui.controller;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.stage.DirectoryChooser;

import org.controlsfx.control.StatusBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.thehilikus.dependency.analysis.api.DependencySource;
import com.github.thehilikus.dependency.analysis.api.Graph;
import com.github.thehilikus.dependency.analysis.core.PackageGraphCreator;
import com.github.thehilikus.dependency.analysis.gui.MainPanel;
import com.github.thehilikus.dependency.analysis.gui.controller.tasks.AbstractAnalyzerTask;
import com.github.thehilikus.dependency.analysis.gui.controller.tasks.GraphCreationTask;
import com.github.thehilikus.dependency.analysis.gui.controller.tasks.JavaSourceCodeSelectionTask;

/**
 * A controller of user events of MainPanel
 *
 * @author hilikus
 */
public class MainController implements UiController {
    private MainPanel mainPanel;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private AbstractAnalyzerTask<Graph> graphTask;

    private DependencySource sourceCodeReader;

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @FXML
    private StatusBar statusBar;
    
    @FXML
    private Parent root;
    
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
	    bindTask(graphTask);

	    executor.execute(graphTask);
	} catch (Exception exc) {
	    log.error("[createPackageGraphSession] There was a problem running a graph creation session: ", exc);
	    mainPanel.reportException("There was an error creating the graph", exc);
	}

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
		AbstractAnalyzerTask<DependencySource> codeSelectionTask = new JavaSourceCodeSelectionTask(rootFolder.toPath());

		bindTask(codeSelectionTask);
		codeSelectionTask.setOnSucceeded(event -> sourceCodeReader = (DependencySource) event.getSource().getValue());

		executor.execute(codeSelectionTask);
	    } catch (Exception exc) {
		log.error("[selectJavaSourceCodeFolder] There was a problem reading source code: ", exc);
		mainPanel.reportException("There was a problem reading source code", exc);
	    }
	}
    }

    private void bindTask(AbstractAnalyzerTask<?> task) {
	statusBar.textProperty().bind(task.messageProperty());
	
	ObjectBinding<Cursor> cursorBinding = Bindings.when(task.runningProperty()).then(Cursor.WAIT).otherwise(Cursor.DEFAULT);
	root.cursorProperty().bind(cursorBinding);
	
	//TODO: unbind after done
    }

    @Override
    public void setMainPanel(MainPanel mainPanel) {
	this.mainPanel = mainPanel;
    }

    @Override
    public void stop() {
	log.debug("[stop] Stopping controller");

	stopExecutor();
    }

    private void stopExecutor() {
	log.trace("[stopExecutor] Stopping executor");

	executor.shutdown();
	try {
	    executor.awaitTermination(5, TimeUnit.SECONDS);
	} catch (InterruptedException exc) {
	    executor.shutdownNow();
	}
	executor.shutdownNow();
    }
}
