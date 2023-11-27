package net.wforbes.omnia.overworld.entity.pathfind;

import javafx.geometry.Point2D;

import java.awt.geom.Line2D;
import java.util.Vector;

public class Path {
    private Vector<Point2D> nodes;

    private Line2D path;

    //TODO: next implementation using a set of morphing nodes to smoothly curve around...
    public Path() {
        this.nodes = new Vector<>();
    }

    public void addNode(Point2D node) {
        this.nodes.add(node);
    }

    public Vector<Point2D> getNodes() {
        return this.nodes;
    }

    public double[] getNextMove(double x, double y) {
        return new double[2];
    }
}
