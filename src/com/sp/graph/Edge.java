package com.sp.graph;

public class Edge {

    private Vertex startVertex = null;
    private Vertex endVertex = null;
    private int weight;
    private String edgeName;

    public Edge(Vertex startVertex, Vertex endVertex, int weight) {
        this.weight = weight;
        this.startVertex = startVertex;
        this.endVertex = endVertex;
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

    public String edgeName() {
        return this.edgeName;
    }

    public void setStartVertex(Vertex startVertex) {
        this.startVertex = startVertex;
    }

    public Vertex getStartVertex() {
        return this.startVertex;
    }

    public void setEndVertex(Vertex endVertex) {
        this.endVertex = endVertex;
    }

    public Vertex getEndtVertex() {
        return this.endVertex;
    }
}
