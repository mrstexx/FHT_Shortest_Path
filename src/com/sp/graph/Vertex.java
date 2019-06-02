package com.sp.graph;

import java.util.LinkedList;
import java.util.List;

public class Vertex {

    public static final int CHANGE_STATION_TIME = 5;
    public boolean isVisited = false;
    private Vertex parentNode = null;
    private List<Edge> neighbors = null;
    private int distance;

    private String name;

    public Vertex(String name) {
        this.name = name;
        this.distance = Integer.MAX_VALUE;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Vertex getParentNode() {
        return this.parentNode;
    }

    public void setParentNode(Vertex parentNode) {
        this.parentNode = parentNode;
    }

    /**
     * Add new edge to the vertex
     * 
     * @param edge edge to be added to the vertex
     */
    public void addEdge(Edge edge) {
        if (this.neighbors == null) {
            this.neighbors = new LinkedList<Edge>();
        }
        if (edge != null) {
            this.neighbors.add(edge);
        }
    }

    public List<Edge> getAllNeighbors() {
        return this.neighbors;
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
