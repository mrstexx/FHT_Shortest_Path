package com.sp.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Graph {

    private List<Vertex> vertices;
    private int shortestTime;
    private Stack<Vertex> shortestPathNodes;

    public Graph() {
        vertices = new ArrayList<>();
        shortestPathNodes = new Stack<>();
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
     * Function used to find shortest path and time between two nodes
     * 
     * @param startNode Node from where to find path
     * @param endNode   Node until where to find path
     */
    public void findShortestPath(Vertex startNode, Vertex endNode) {
        Heap heap = new Heap();
        heap.put(startNode, 0);
        while (!heap.isEmpty()) {
            Vertex extractedNode = heap.get();
            if (extractedNode.getName().equals(endNode.getName())) {
                break;
            }

            List<Edge> neighbors = extractedNode.getAllNeighbors();
            checkNeighbors(neighbors, heap);
        }
        setShortestNodes(endNode);
        this.shortestTime = endNode.getDistance();
    }

    private void checkNeighbors(List<Edge> neighbors, Heap heap) {
        for (Edge neighbour : neighbors) {
            Vertex sourceNode = neighbour.getSource();
            Vertex destinationNode = neighbour.getDestination();
            int weight = neighbour.getWeight();
            int nodeDistance = weight + sourceNode.getDistance();
            if (nodeDistance < destinationNode.getDistance()) {
                destinationNode.setParentNode(sourceNode);
                if (!destinationNode.isVisited) {
                    heap.put(destinationNode, nodeDistance);
                    destinationNode.isVisited = true;
                }
            }
        }
    }

    private void setShortestNodes(Vertex node) {
        while (node != null) {
            this.shortestPathNodes.push(node);
            node = node.getParentNode();
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
                    System.out.print(edge.getDestination().getName() + " -> ");
                }
                System.out.println("null");
            }
        }
    }

    /**
     * @return Shortest time
     */
    public int getShortestTime() {
        return this.shortestTime;
    }

    /**
     * @return List of all stations between two entered nodes
     */
    public Stack<Vertex> getShortestPathNodes() {
        return this.shortestPathNodes;
    }
}
