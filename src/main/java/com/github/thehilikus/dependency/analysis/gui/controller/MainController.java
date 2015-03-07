package com.github.thehilikus.dependency.analysis.gui.controller;

import java.io.File;

import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;

import com.github.thehilikus.dependency.analysis.gui.MainPanel;

/**
 * A controller of user events of MainPanel
 *
 * @author hilikus
 */
public class MainController {
    private MainPanel mainPanel;

    @FXML
    private void initialize() {

    }

    @FXML
    private void createPackageGraphSession() {

    }

    @FXML
    private void selectSourceCodeFolder() {
	DirectoryChooser folderChooser = new DirectoryChooser();
	folderChooser.setTitle("Select root folder to scan for java source code");
	File rootFolder = folderChooser.showDialog(mainPanel.getPrimaryStage());
	if (rootFolder != null) {

	}

    }

    /**
     * @param mainPanel the first panel to load
     */
    public void setMainPanel(MainPanel mainPanel) {
	this.mainPanel = mainPanel;

    }
}
