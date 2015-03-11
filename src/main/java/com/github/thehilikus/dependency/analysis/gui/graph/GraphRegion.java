package com.github.thehilikus.dependency.analysis.gui.graph;

import java.awt.Dimension;
import java.awt.geom.Point2D;

import com.github.thehilikus.dependency.analysis.adapters.JungGraphAdapter;
import com.github.thehilikus.dependency.analysis.api.Graph;

import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.util.Relaxer;
import edu.uci.ics.jung.algorithms.layout.util.VisRunner;
import edu.uci.ics.jung.algorithms.util.IterativeContext;
import edu.uci.ics.jung.graph.util.Pair;

public class GraphRegion extends Region {

    
    private double CIRCLE_SIZE = 25;
    private Layout<String, String> layout;
    private Relaxer relaxer;

    public GraphRegion(Graph graph) {
	JungGraphAdapter jungGraph = new JungGraphAdapter(graph);
	layout = new CircleLayout<>(jungGraph);
    }
    
    
    /*
     * Taken from https://gist.github.com/jrguenther/9d0c37329f9928a2b56e
     * Author: Jeffrey Guenther
     */
    @Override
    protected void layoutChildren() {
	super.layoutChildren();
	layout.setSize(new Dimension(widthProperty().intValue(), heightProperty().intValue()));
	// relax the layout
	if (relaxer != null) {
	    relaxer.stop();
	    relaxer = null;
	}
	if (layout instanceof IterativeContext) {
	    layout.initialize();
	    if (relaxer == null) {
		relaxer = new VisRunner((IterativeContext) this.layout);
		relaxer.prerelax();
		relaxer.relax();
	    }
	}
	edu.uci.ics.jung.graph.Graph<String, String> graph = layout.getGraph();
	// draw the vertices in the graph
	for (String v : graph.getVertices()) {
	    // Get the position of the vertex
	    Point2D p = layout.transform(v);
	    // draw the vertex as a circle
	    Circle circle = new Circle(p.getX(), p.getY(), CIRCLE_SIZE);
	    // add it to the group, so it is shown on screen
	    this.getChildren().add(circle);
	}

	// draw the edges
	for (String e : graph.getEdges()) {
	    // get the end points of the edge
	    Pair<String> endpoints = graph.getEndpoints(e);
	    // Get the end points as Point2D objects so we can use them in the
	    // builder
	    Point2D pStart = layout.transform(endpoints.getFirst());
	    Point2D pEnd = layout.transform(endpoints.getSecond());
	    // Draw the line
	    Line line = new Line(pStart.getX(), pStart.getY(), pEnd.getX(), pEnd.getY());
	    // add the edges to the screen
	    this.getChildren().add(line);
	}
    }
}
