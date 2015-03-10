package com.github.thehilikus.dependency.analysis.gui;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.thehilikus.dependency.analysis.gui.controller.UiController;

/**
 * The main panel of the application
 *
 * @author hilikus
 */
public class MainPanel extends Application {

    private Stage primaryStage;

    private BorderPane rootLayout;

    private static final Logger log = LoggerFactory.getLogger(MainPanel.class);

    private UiController controller;

    @Override
    public void start(Stage primaryStageFX) {
	this.primaryStage = primaryStageFX;
	this.primaryStage.setTitle("Code Dependency Analyzer");

	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MainPanel.fxml"));
	    initRootLayout(loader);
	    connectController(loader);
	} catch (IOException exc) {
	    log.error("[start] Error creating main panel", exc);
	}
    }

    @Override
    public void stop() throws Exception {
	log.debug("[stop] Application is stopping");

	controller.stop();
    }

    private void connectController(FXMLLoader loader) {
	controller = loader.getController();

	controller.setMainPanel(this);
    }

    private void initRootLayout(FXMLLoader loader) throws IOException {

	rootLayout = loader.load();

	Scene scene = new Scene(rootLayout);
	primaryStage.setScene(scene);
	primaryStage.show();
    }

    /**
     * @return the main stage
     */
    public Stage getPrimaryStage() {
	return primaryStage;
    }

    /**
     * @param args ignored
     */
    public static void main(String[] args) {
	launch(args);
    }

    /**
     * Reports an error
     * 
     * @param errorMessage the message explaining the problem
     */
    public void reportError(String errorMessage) {
	Alert alert = new Alert(AlertType.ERROR);
	alert.setTitle("Error");
	alert.setHeaderText(null);
	alert.setContentText(errorMessage);

	alert.showAndWait();
    }

    /**
     * Reports an exception and its stack trace
     * 
     * @param errorMessage the message explaining the problem
     * @param exception
     */
    public void reportException(String errorMessage, Exception exception) {
	Alert alert = new Alert(AlertType.ERROR);
	alert.setTitle("Exception Caught");
	alert.setContentText(errorMessage);

	StringWriter stringWriter = new StringWriter();
	PrintWriter printWriter = new PrintWriter(stringWriter);
	exception.printStackTrace(printWriter);
	String exceptionText = stringWriter.toString();

	Label label = new Label("The exception stacktrace was:");

	TextArea textArea = new TextArea(exceptionText);
	textArea.setEditable(false);
	textArea.setWrapText(true);

	textArea.setMaxWidth(Double.MAX_VALUE);
	textArea.setMaxHeight(Double.MAX_VALUE);
	GridPane.setVgrow(textArea, Priority.ALWAYS);
	GridPane.setHgrow(textArea, Priority.ALWAYS);

	GridPane expContent = new GridPane();
	expContent.setMaxWidth(Double.MAX_VALUE);
	expContent.add(label, 0, 0);
	expContent.add(textArea, 0, 1);

	// Set expandable Exception into the dialog pane.
	alert.getDialogPane().setExpandableContent(expContent);

	alert.showAndWait();
    }

}
