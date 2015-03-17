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
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.DirectoryChooser;

import org.controlsfx.control.StatusBar;
import org.jgrapht.graph.DefaultEdge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.thehilikus.dependency.analysis.adapters.JGraphXAdapterrrr;
import com.github.thehilikus.dependency.analysis.api.DependencySource;
import com.github.thehilikus.dependency.analysis.api.Graph;
import com.github.thehilikus.dependency.analysis.core.PackageGraphCreator;
import com.github.thehilikus.dependency.analysis.gui.MainPanel;
import com.github.thehilikus.dependency.analysis.gui.controller.tasks.AbstractAnalyzerTask;
import com.github.thehilikus.dependency.analysis.gui.controller.tasks.GraphCreationTask;
import com.github.thehilikus.dependency.analysis.gui.controller.tasks.JavaSourceCodeSelectionTask;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxIGraphModel;

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
    private AnchorPane center;

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
	    graphTask.setOnSucceeded(event -> displayGraph(graphTask.getValue()));

	    bindAndRunTask(graphTask);
	} catch (Exception exc) {
	    log.error("[createPackageGraphSession] There was a problem running a graph creation session: ", exc);
	    mainPanel.reportException("There was an error creating the graph", exc);
	}

    }

    private void displayGraph(Graph newGraph) {
	JGraphXAdapterrrr<String, DefaultEdge> jgxAdapter = new JGraphXAdapterrrr<>(
		(org.jgrapht.Graph<String, DefaultEdge>) newGraph); // TODO: fix hack
	
	log.debug("center witdh = {}, height = {}", center.getWidth(), center.getHeight());
	center.getChildren().clear();
	
	
	mxFastOrganicLayout layout = new mxFastOrganicLayout(jgxAdapter);
	//interesting mxOrganicLayout layout = new mxOrganicLayout(jgxAdapter);
	
	layout.execute(jgxAdapter.getDefaultParent());
	mxIGraphModel model = jgxAdapter.getModel();
	int verticesCount = model.getChildCount(jgxAdapter.getDefaultParent());
	long timeStart = System.nanoTime();
	for (int pos = 0; pos < verticesCount; pos++) {
	    Object cell = model.getChildAt(jgxAdapter.getDefaultParent(), pos);
	    if (model.isVertex(cell)) {
		mxGeometry geometry = model.getGeometry(cell);
		Circle node = new Circle(geometry.getCenterX(), geometry.getCenterY(), 5);
		Tooltip.install(node, new Tooltip(model.getValue(cell).toString()));
		log.debug("[displayGraph] Created vertex {}", node);
		center.getChildren().add(node);
	    } else {
		//vertex
		Object source = model.getTerminal(cell, true);
		mxGeometry geometrySource = model.getGeometry(source);
		Object dest = model.getTerminal(cell, false);
		mxGeometry geometryDest = model.getGeometry(dest);
		Line line = new Line(geometrySource.getCenterX(), geometrySource.getCenterY(), geometryDest.getCenterX(), geometryDest.getCenterY());
		log.debug("[displayGraph] Created edge {}", line);
		center.getChildren().add(line);
	    }
	}
	long timeElapsed = System.nanoTime()-timeStart;
	log.info("[displayGraph] Created graph with {} nodes in {} milliseconds", verticesCount, TimeUnit.MILLISECONDS.convert(timeElapsed, TimeUnit.NANOSECONDS));
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
		AbstractAnalyzerTask<DependencySource> codeSelectionTask = new JavaSourceCodeSelectionTask(
			rootFolder.toPath());

		codeSelectionTask.setOnSucceeded(event -> sourceCodeReader = (DependencySource) event.getSource()
			.getValue());
		bindAndRunTask(codeSelectionTask);
	    } catch (Exception exc) {
		log.error("[selectJavaSourceCodeFolder] There was a problem reading source code: ", exc);
		mainPanel.reportException("There was a problem reading source code", exc);
	    }
	}
    }

    private void bindAndRunTask(AbstractAnalyzerTask<?> task) {
	statusBar.textProperty().bind(task.messageProperty());

	ObjectBinding<Cursor> cursorBinding = Bindings.when(task.runningProperty()).then(Cursor.WAIT)
		.otherwise(Cursor.DEFAULT);
	root.cursorProperty().bind(cursorBinding);

	// TODO: unbind after done

	log.debug("[bindAndRunTask] Finished binding {} to the UI, Ready to execute it", task);
	executor.execute(task);
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
