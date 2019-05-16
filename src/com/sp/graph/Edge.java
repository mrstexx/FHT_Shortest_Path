package com.sp.graph;

public class Edge {

    private Vertex destinationVertex = null;
    private int weight;
    private String edgeName;

    public Edge(Vertex startVertex, Vertex destinationVertex, int weight) {
        this.weight = weight;
        this.destinationVertex = destinationVertex;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setEdgeName(String edgeName) {
        this.edgeName = edgeName;
    }

    public String getEdgeName() {
        return this.edgeName;
    }

    public void setDestinationVertex(Vertex destinationVertex) {
        this.destinationVertex = destinationVertex;
    }

    public Vertex getDestinationVertex() {
        return this.destinationVertex;
    }
}
