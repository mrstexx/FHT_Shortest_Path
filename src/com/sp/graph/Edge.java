package com.sp.graph;

public class Edge {

    private Vertex source = null;
    private Vertex destination = null;
    private int weight;
    private String edgeName;

    public Edge(Vertex source, Vertex destination, int weight, String edgeName) {
        this.weight = weight;
        this.source = source;
        this.destination = destination;
        this.edgeName = edgeName;
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

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

    public Vertex getDestination() {
        return this.destination;
    }

    public void setSource(Vertex source) {
        this.source = source;
    }

    public Vertex getSource() {
        return this.source;
    }
}
