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

    public void addEdge(Edge edge) {
        if (this.neighbors == null) {
            this.neighbors = new LinkedList<Edge>();
        }
        this.neighbors.add(edge);
    }

    public List<Edge> getAllNeighbors() {
        return this.neighbors;
    }

}
