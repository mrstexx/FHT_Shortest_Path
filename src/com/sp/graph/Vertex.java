package com.sp.graph;

import java.util.LinkedList;
import java.util.List;

public class Vertex {

    public static final int CHANGE_STATION_TIME = 5;
    private List<Edge> neighbors = null;
    private int vertexWeight;

    private String name;

    public Vertex(String name) {
        this.name = name;
        this.vertexWeight = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getVertexWeight() {
        return this.vertexWeight;
    }

    public void setVertexWeight(int weight) {
        this.vertexWeight = weight;
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
