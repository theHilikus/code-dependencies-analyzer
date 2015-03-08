package com.github.thehilikus.dependency.analysis.gui.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;

import com.github.thehilikus.dependency.analysis.api.DependencySource;
import com.github.thehilikus.dependency.analysis.api.Graph;
import com.github.thehilikus.dependency.analysis.core.PackageGraphCreator;
import com.github.thehilikus.dependency.analysis.gui.MainPanel;
import com.github.thehilikus.dependency.analysis.readers.java.JavaSourceCodeReader;
import com.github.thehilikus.dependency.analysis.sessions.GraphCreationSession;

/**
 * A controller of user events of MainPanel
 *
 * @author hilikus
 */
public class MainController {
    private MainPanel mainPanel;

    private Set<DependencySource> dependencySources = new HashSet<>();

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private Future<Graph> operation;

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @FXML
    private void createPackageGraphSession() {
	if (dependencySources.isEmpty()) {
	    log.error("[createPackageGraphSession] Please set a dependency provider first");
	    // TODO: add Alert dialog when openjfx 8u40 is released
	    return;
	}
	PackageGraphCreator packageGraph = new PackageGraphCreator();
	GraphCreationSession graphSession = new GraphCreationSession("test", packageGraph, dependencySources);
	
	if (operation != null && !operation.isDone()) {
	    log.info("[createPackageGraphSession] Previous task was not finished. Cancelling it");
	    operation.cancel(true);
	}
	
	operation = executor.submit(graphSession);
    }

    @FXML
    private void selectJavaSourceCodeFolder() {
	dependencySources.clear();

	DirectoryChooser folderChooser = new DirectoryChooser();
	folderChooser.setTitle("Select root folder to scan for java source code");
	File rootFolder = folderChooser.showDialog(mainPanel.getPrimaryStage());
	if (rootFolder != null) {
	    try {
		JavaSourceCodeReader sourceCodeReader = new JavaSourceCodeReader(rootFolder.toPath());
		dependencySources.add(sourceCodeReader);
		log.info("[selectJavaSourceCodeFolder] Java sourceCode reader initialized successfully");
	    } catch (IOException exc) {
		log.error("[selectJavaSourceCodeFolder] There was a problem reading source code: ", exc);
		// TODO: add Alert dialog when openjfx 8u40 is released
	    }
	}
    }

    /**
     * @param mainPanel the first panel to load
     */
    public void setMainPanel(MainPanel mainPanel) {
	this.mainPanel = mainPanel;
    }
}
