package com.sp.graph;

import java.util.LinkedList;
import java.util.List;

public class Graph {

    private List<Vertex> vertices;
    private List<Edge> edges;

    public Graph() {
        vertices = new LinkedList<Vertex>();
        edges = new LinkedList<Edge>();
    }

    public void addVertex(String name) {
        Vertex vertex = new Vertex(name);
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
        }
    }

    public void addEdge(Vertex firstStation, Vertex secondStation, int weight) {
        Edge edge = new Edge(firstStation, secondStation, weight);
        edges.add(edge);
    }

    public List<Vertex> getAllVertices() {
        return this.vertices;
    }

    public List<Edge> getAllEdges() {
        return this.edges;
    }

    public void printAll() {

    }
}
