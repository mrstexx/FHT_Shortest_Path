package com.sp.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Graph {

    private Map<String, Vertex> vertices;
    private int shortestTime;
    private Stack<Vertex> shortestPathNodes;

    public List<Vertex> allVertices = new ArrayList<>();

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
     * @deprecated
     * 
     *             NOT USED ANYMORE
     * 
     *             Function used to find shortest path and time between two nodes
     *             Run Dijkstra!
     *
     * @param startNode Node from where to find path
     * @param endNode   Node until where to find path
     */
    public void _findShortestPath(Vertex startNode, Vertex endNode) {
        Heap heap = new Heap();
        startNode.setDistance(0);
        heap.put(startNode);

        System.out.println("---------------");

        while (!heap.isEmpty()) {
            Vertex extractedNode = heap.get();
            // System.out.println("***" + extractedNode.getName() + " : " +
            // extractedNode.getDistance());
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

    /**
     * @deprecated
     * 
     *             FUNCTION NOT USED ANYMORE
     * 
     * @param neighbors
     * @param heap
     */
    private void checkNeighbors(List<Edge> neighbors, Heap heap) {
        for (Edge edge : neighbors) {
            Vertex sourceNode = edge.getSource();
            Vertex destinationNode = edge.getDestination();

            // in case one is visited, but after switching another node has better distance
            if (!destinationNode.isVisited) {
                int weight = edge.getWeight();

                if (sourceNode.getName().equals(destinationNode.getName()) && sourceNode.getParentNode() == null) {
                    weight = 0;
                } else if (sourceNode.getName().equals(destinationNode.getName())
                        && sourceNode.getParentNode() != null) {
                    if (!detectChangeDirection(sourceNode, edge.getEdgeName())) {
                        weight = 0;
                    }
                }

                int nodeDistance = weight + sourceNode.getDistance();

                if (detectChangeDirection(sourceNode, edge.getEdgeName())) {
                    nodeDistance += Vertex.CHANGE_LINE_TIME;
                }

                if (nodeDistance <= destinationNode.getDistance()) {
                    destinationNode.setParentNode(sourceNode);
                    destinationNode.setDistance(nodeDistance);
                    heap.put(destinationNode);
                    destinationNode.isVisited = true;
                }
            }
        }
    }

    /**
     * Function used to find shortest path and time between two nodes Run Dijkstra!
     *
     * @param startNode Node from where to find path
     * @param endNode   Node until where to find path
     */
    public void findShortestPath(Vertex startNode, Vertex endNode) {
        Heap heap = new Heap();
        int minDistance = 0;
        do {
            // TODO Debugging line -> should be removed
            System.out.println("START: " + startNode.getName() + " : " + startNode.getDistance());
            startNode.isVisited = true;

            for (Edge edge : startNode.getAllNeighbors()) {
                Vertex sourceNode = edge.getSource();
                Vertex destinationNode = edge.getDestination();
                if (!destinationNode.isVisited) {
                    int weight = edge.getWeight();

                    // check for changing stations in the beginning (we don't want to count this
                    // time)
                    if (sourceNode.getName().equals(destinationNode.getName()) && startNode.getParentNode() == null) {
                        weight = 0;
                    } else if (sourceNode.getName().equals(destinationNode.getName())
                            && startNode.getParentNode() != null) {
                        if (!detectChangeDirection(startNode, edge.getEdgeName())) {
                            weight = 0;
                        }
                    }

                    // !!!Adding time to changing direction not required anymore because of layered
                    // lines handling!!!
                    /*
                     * if (detectChangeDirection(startNode, edge.getEdgeName())) { // add 5 minutes
                     * more on changing direction (line) weight += Vertex.CHANGE_LINE_TIME; }
                     */

                    weight += minDistance;

                    if (destinationNode.getParentNode() == null) {
                        destinationNode.setParentNode(startNode);
                        destinationNode.setDistance(weight);
                    } else {
                        if (weight <= destinationNode.getDistance()) {
                            destinationNode.setParentNode(startNode);
                            destinationNode.setDistance(weight);
                        }
                    }

                    // TODO Debugging line -> Should be removed
                    System.out.println(destinationNode.getName() + " : " + destinationNode.getDistance());
                    // insert node into heap
                    heap.put(destinationNode);
                }
            }

            do {
                if (!heap.isEmpty()) {
                    Vertex extractedNode = heap.get();
                    minDistance = extractedNode.getDistance();
                    startNode = extractedNode;
                }
            } while (startNode != null && startNode.isVisited);
        } while (startNode != null && !startNode.getName().equals(endNode.getName()));
        // set shortest time to object
        this.shortestTime = minDistance;
        // go over parent nodes in order to reverse them
        setShortestNodes(startNode);
        // reset all values after in order to have default state for the next search
        resetNodes();
    }

    private boolean detectChangeDirection(Vertex startNode, String currentEdgeName) {
        // detect changing direction between two station (using parent node)
        if (startNode.getParentNode() != null) {
            Edge edge = getEdgeBetweenTwoStations(startNode.getParentNode(), startNode);
            if (edge == null) {
                return false;
            }
            return !edge.getEdgeName().equals(currentEdgeName);
        }
        return false;
    }

    /**
     * @deprecated Function not used anymore because program data structure was
     *             changed
     */
    @SuppressWarnings("unused")
    private void __resetNodes() {
        // reset parentNode, distance from the source and line names between two
        // searches for shortest time
        for (Map.Entry<String, Vertex> entry : vertices.entrySet()) {
            Vertex node = getVertex(entry.getKey());
            if (node != null) {
                node.setDistance(Integer.MAX_VALUE);
                node.isVisited = false;
                node.setParentNode(null);
                node.setCurrentLineName("");
            }
        }
    }

    private void resetNodes() {
        // between each new search for shortest path, we set each node to the default
        // state
        for (Vertex node : allVertices) {
            if (node != null) {
                node.setDistance(Integer.MAX_VALUE);
                node.isVisited = false;
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
                if (edge.getSource().getName().equals(startNode.getName())
                        && (endNode == null || edge.getDestination().getName().equals(endNode.getName()))) {
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
        String previousNodeName = "";
        String previousLineName = "";
        while (node != null) {
            // TODO Debugging lines -> should be removed
            System.out.println("----------");
            System.out.println(node.getName() + " : " + node.getDistance());
            correctLineName(node);
            if (!node.getCurrentLineName().equals("Switching")) {
                this.shortestPathNodes.push(new Vertex(node));
                previousLineName = node.getCurrentLineName();
            } else {
                if (!previousLineName.equals("Switching") && !previousNodeName.equals(node.getName())) {
                    this.shortestPathNodes.push(new Vertex(node));
                    node.setCurrentLineName(previousLineName);
                    previousLineName = node.getCurrentLineName();
                }
            }
            previousNodeName = node.getName();
            node = node.getParentNode();
        }
    }

    private void correctLineName(Vertex node) {
        // set correct line names on switching direction
        if (node.getParentNode() != null) {
            Edge line = getEdgeBetweenTwoStations(node.getParentNode(), node);
            if (!line.getEdgeName().equals("Switching")) {
                node.setCurrentLineName(line.getEdgeName());
            }
            node.getParentNode().setCurrentLineName(line.getEdgeName());
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
     * Print all vertices and its edges (including switching)
     */
    public void printAll() {
        for (Map.Entry<String, Vertex> nodeEntry : this.vertices.entrySet()) {
            System.out.print(nodeEntry.getKey() + " -> ");
            if (((Vertex) nodeEntry.getValue()).getAllNeighbors() != null) {
                for (Edge edge : ((Vertex) nodeEntry.getValue()).getAllNeighbors()) {
                    System.out.print(edge.getDestination().getName() + "(" + edge.getEdgeName() + ")" + " -> ");
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
