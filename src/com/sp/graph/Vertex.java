package com.sp.graph;

import java.util.LinkedList;
import java.util.List;

public class Vertex {

    public static final int DEFAULT_TIME = 5;
    private List<Edge> neighbors = null;

    private String name;

    public Vertex(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
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

}
