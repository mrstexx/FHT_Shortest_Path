package com.sp.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private List<Vertex> vertices;

    public Graph() {
        vertices = new ArrayList<Vertex>();
    }

    public void addVertex(String name) {
        Vertex vertex = new Vertex(name);
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
        }
    }

    public boolean contains(String name) {
        for (Vertex vertex : this.vertices) {
            if (vertex.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Vertex getVertex(String name) {
        for (Vertex vertex : this.vertices) {
            if (vertex.getName().equals(name)) {
                return vertex;
            }
        }
        return null;
    }

    public List<Vertex> getAllVertices() {
        return this.vertices;
    }

    public void printAll() {
        for (Vertex vertex : this.vertices) {
            System.out.print(vertex.getName() + " -> ");
            for (Edge edge : vertex.getAllNeighbors()) {
                System.out.print(edge.getDestinationVertex().getName() + " -> ");
            }
            System.out.println();
        }
    }
}
