package com.sp.graph;

import com.sun.istack.internal.Nullable;
import sun.misc.Version;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Graph {

    private Map<String, Vertex> vertices;
    private int shortestTime;
    private Stack<Vertex> shortestPathNodes;
    private Map<String, Boolean> visited;

    public Graph() {
        vertices = new HashMap<>();
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
            vertices.put(node.getName(), node);
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
     * Run Dijkstra!
     *
     * @param startNode Node from where to find path
     * @param endNode   Node until where to find path
     */
    public void findShortestPath(Vertex startNode, Vertex endNode) {
        visited = new HashMap<String, Boolean>();
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
        resetNodes();
    }

    private void checkNeighbors(List<Edge> neighbors, Heap heap) {
        for (Edge edge : neighbors) {
            Vertex sourceNode = edge.getSource();
            Vertex destinationNode = edge.getDestination();

            int nodeDistance = edge.getWeight() + sourceNode.getDistance();

            if (visited.get(destinationNode.getName()) == null) {

                // destinationNode.setCurrentLineName(neighbour.getEdgeName());

                if (detectChangeDirection(sourceNode, destinationNode, edge.getEdgeName())) {
                    nodeDistance += Vertex.CHANGE_LINE_TIME;
                }

                if (nodeDistance < destinationNode.getDistance()) {
                    destinationNode.setParentNode(sourceNode);
                    heap.put(destinationNode, nodeDistance);
                    visited.put(destinationNode.getName(), true);
                }
            }
        }
    }

    private boolean detectChangeDirection(Vertex startNode, Vertex endNode, String currentEdgeName) {
        if (startNode.getParentNode() != null) {
            Edge edge = getEdgeBetweenTwoStations(startNode.getParentNode(), startNode);
            if (edge == null) {
                return false;
            }
            return edge.getEdgeName().equals(currentEdgeName);
        }
        return false;
    }

    private void resetNodes() {
        // reset parentNode, distance from the source and line names between two searches for shortest time
        for (Map.Entry<String, Vertex> entry : vertices.entrySet()) {
            Vertex node = getVertex(entry.getKey());
            if (node != null) {
                node.setDistance(Integer.MAX_VALUE);
                node.setParentNode(null);
                node.setCurrentLineName("");
            }
        }
    }

    private Edge getEdgeBetweenTwoStations(Vertex startNode, Vertex endNode) {
        List<Edge> neighbors = null;
        if (startNode == null && endNode == null) {
            return null;
        }
        if (endNode != null) {
            neighbors = endNode.getAllNeighbors();
        }
        if (startNode != null) {
            neighbors = startNode.getAllNeighbors();
        }
        for (Edge edge : neighbors) {
            if (startNode != null) {
                if (edge.getSource().getName().equals(startNode.getName()) &&
                        (endNode == null || edge.getDestination().getName().equals(endNode.getName()))) {
                    return edge;
                }
            }
            if (endNode != null) {
                if (edge.getDestination().getName().equals(endNode.getName())) {
                    return edge;
                }
            }
        }
        return null;
    }

    private void setShortestNodes(Vertex node) {
        while (node != null) {
            this.shortestPathNodes.push(new Vertex(node));
            node = node.getParentNode();
        }
    }

    /**
     * @param name name of the vertex
     * @return returns true if vertex exists, otherwise false
     */
    public boolean contains(String name) {
        return this.vertices.get(name) != null;
    }

    /**
     * @param name name of the vertex
     * @return returns vertex if existing or null
     */
    @Nullable
    public Vertex getVertex(String name) {
        if (this.vertices.get(name) != null) {
            return this.vertices.get(name);
        }
        return null;
    }

    /**
     * @return Returns list of all graph vertices
     */
    public Map<String, Vertex> getAllVertices() {
        return this.vertices;
    }

    /**
     * Print all vertices and edges
     */
    public void printAll() {
        for (Map.Entry<String, Vertex> nodeEntry : this.vertices.entrySet()) {
            System.out.print(nodeEntry.getKey() + " -> ");
            if (((Vertex) nodeEntry.getValue()).getAllNeighbors() != null) {
                for (Edge edge : ((Vertex) nodeEntry.getValue()).getAllNeighbors()) {
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
