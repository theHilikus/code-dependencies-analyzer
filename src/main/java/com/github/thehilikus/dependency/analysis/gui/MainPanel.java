package com.github.thehilikus.dependency.analysis.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.thehilikus.dependency.analysis.gui.controller.MainController;

/**
 * The main panel of the application
 *
 * @author hilikus
 */
public class MainPanel extends Application {

    private Stage primaryStage;

    private BorderPane rootLayout;

    private FXMLLoader loader = new FXMLLoader();

    private static final Logger log = LoggerFactory.getLogger(MainPanel.class);

    @FXML
    private Menu myMenu;

    @Override
    public void start(Stage primaryStageFX) {
	this.primaryStage = primaryStageFX;
	this.primaryStage.setTitle("Code Dependency Analyzer");

	try {
	    initRootLayout();
	    connectController();
	} catch (IOException exc) {
	    log.error("[start] Error creating main panel", exc);
	}
    }

    private void connectController() {
	MainController controller = loader.getController();

	controller.setMainPanel(this);

    }

    private void initRootLayout() throws IOException {
	loader.setLocation(getClass().getResource("view/MainPanel.fxml"));

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
}
