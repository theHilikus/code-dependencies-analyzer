package com.github.thehilikus.dependency.analysis.gui.controller;

import com.github.thehilikus.dependency.analysis.gui.MainPanel;

/**
 * A javafx Controller for MainPanel
 *
 * @author hilikus
 */
public interface UiController {

    /**
     * Links the panel to the controller
     * 
     * @param mainPanel the first panel to load
     */
    public void setMainPanel(MainPanel mainPanel);

    /**
     * Cleans up all the resources
     */
    public default void stop() {
    }
}
