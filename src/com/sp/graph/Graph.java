package com.sp.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private List<Vertex> vertices;

    public Graph() {
        vertices = new ArrayList<Vertex>();
    }

    /**
     * Function used to add new Vertex to the graph
     * 
     * @param node vertex to be added in the graph
     */
    public void addVertex(Vertex node) {
        // check if vertex with passed name is existing, if not than add new vertex
        if (!contains(node.getName())) {
            vertices.add(node);
            return;
        }
        // if it is existing, then go through edges and add them to existing vertex
        Vertex existingNode = getVertex(node.getName());
        if (node.getAllNeighbors() != null && existingNode != null) {
            for (Edge edge : node.getAllNeighbors()) {
                existingNode.addEdge(edge);
            }
        }
    }

    /**
     * @param name name of the vertex
     * @return returns true if vertex exists, otherwise false
     */
    public boolean contains(String name) {
        for (Vertex node : this.vertices) {
            if (node.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param name name of the vertex
     * @return returns vertex if existing or null
     */
    public Vertex getVertex(String name) {
        for (Vertex node : this.vertices) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    /**
     * @return Returns list of all graph vertices
     */
    public List<Vertex> getAllVertices() {
        return this.vertices;
    }

    /**
     * Print all vertices and edges
     */
    public void printAll() {
        for (Vertex node : this.vertices) {
            System.out.print(node.getName() + " -> ");
            if (node.getAllNeighbors() != null) {
                for (Edge edge : node.getAllNeighbors()) {
                    System.out.print(edge.getDestinationVertex().getName() + " -> ");
                }
                System.out.println("null");
            }
        }
    }
}
