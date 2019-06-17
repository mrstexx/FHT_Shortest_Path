package com.sp.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphManager {

    private Graph graph;
    private String shortestTime;

    private Map<String, List<Vertex>> tempVertices;

    public GraphManager() {
        graph = new Graph();
        tempVertices = new HashMap<>();
    }

    /**
     * @param startNodeName Name of start station
     * @param endNodeName   Name of end station
     * @return List of all station between
     */
    public List<Vertex> getShortestPath(String startNodeName, String endNodeName) {
        Vertex startNode = graph.getVertex(startNodeName);
        Vertex endNode = graph.getVertex(endNodeName);
        if (startNode != null && endNode != null) {
            graph.findShortestPath(startNode, endNode);
            setShortestTime(graph.getShortestTime());
            Stack<Vertex> stackData = graph.getShortestPathNodes();
            List<Vertex> resultData = new ArrayList<>();
            if (stackData != null) {
                while (!stackData.isEmpty()) {
                    Vertex node = stackData.pop();
                    // System.out.println(node.getName());
                    resultData.add(node);
                }
            }
            return resultData;
        }
        return null;
    }

    /**
     * Function used to load and import vertices into graph
     *
     * @param fileName Name of the file from where to load data
     */
    @SuppressWarnings("resource")
    public void importNewData(String fileName) {
        File file = new File(fileName);
        try {
            BufferedReader bufferReader = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = bufferReader.readLine()) != null) {
                if (line.equals("")) {
                    continue;
                }
                ArrayList<String> parsedLine = getParsedString(line);
                iterateLines(parsedLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mergeTempNodesAndGraph();
        // NOTE Temp here for debugging to have overview of created edges between
        // vertices
        graph.printAll();
    }

    private void mergeTempNodesAndGraph() {
        for (Map.Entry<String, List<Vertex>> nodeEntry : this.tempVertices.entrySet()) {
            if (nodeEntry.getValue().size() == 1) {
                graph.addVertex(nodeEntry.getValue().get(0));
            } else {
                Vertex mainSubNode = nodeEntry.getValue().get(0);
                for (int i = 1; i < nodeEntry.getValue().size(); i++) {
                    mainSubNode.addEdge(
                            new Edge(mainSubNode, nodeEntry.getValue().get(i), Vertex.CHANGE_LINE_TIME, "Switching"));
                    graph.allVertices.add(nodeEntry.getValue().get(i));

                    nodeEntry.getValue().get(i).addEdge(
                            new Edge(nodeEntry.getValue().get(i), mainSubNode, Vertex.CHANGE_LINE_TIME, "Switching"));
                }
                graph.addVertex(mainSubNode);
            }
        }
    }

    private Vertex checkSubnodesAndGetNode(String lineName, List<Vertex> subNodes) {
        for (Vertex node : subNodes) {
            if (node.getDefaultLineName().equals(lineName)) {
                return node;
            }
        }
        return null;
    }

    private void iterateLines(List<String> parsedLine) {
        String lineName = parsedLine.get(0);
        for (int i = 1; i < parsedLine.size(); i += 2) {
            // iterate each second because from the input format, between is the weight of
            // the edge
            Vertex node = null;
            List<Vertex> nodeLayer = null;
            // boolean isLayeredNode = false;
            if (parsedLine.get(i).equals("Stephansplatz") || parsedLine.get(i).equals("Stubentor")) {
                System.out.println("SP");
            }
            if (tempVertices.get(parsedLine.get(i)) != null) {
                // take always first from layer list
                Vertex subNode = checkSubnodesAndGetNode(lineName, tempVertices.get(parsedLine.get(i)));
                if (subNode == null) {
                    // isLayeredNode = true;
                    node = new Vertex(parsedLine.get(i));
                    // add it to list of existing
                    tempVertices.get(parsedLine.get(i)).add(node);

                    // IMPL
                    graph.allVertices.add(node);
                } else {
                    node = subNode;
                }
            } else {
                node = new Vertex(parsedLine.get(i));
                nodeLayer = new ArrayList<>();
                nodeLayer.add(node);
                tempVertices.put(node.getName(), nodeLayer);

                // IMPL
                graph.allVertices.add(node);
            }

            // default name used to recognize difference while probing map
            node.setDefaultLineName(lineName);

            Edge edge = null;
            Vertex destinationNode = null;
            if (i + 1 < parsedLine.size()) {
                // first create edge in one direction
                // check does destination already exists
                if (tempVertices.get(parsedLine.get(i + 2)) != null) {
                    Vertex subNode = checkSubnodesAndGetNode(lineName, tempVertices.get(parsedLine.get(i + 2)));
                    if (subNode == null) {
                        // isLayeredNode = true;
                        destinationNode = new Vertex(parsedLine.get(i + 2));
                        destinationNode.setDefaultLineName(lineName);
                        // TEST
                        tempVertices.get(destinationNode.getName()).add(destinationNode);

                        // IMPL
                        graph.allVertices.add(destinationNode);
                    } else {
                        destinationNode = subNode;
                    }
                } else {
                    destinationNode = new Vertex(parsedLine.get(i + 2));
                    destinationNode.setDefaultLineName(lineName);
                    nodeLayer = new ArrayList<Vertex>();
                    nodeLayer.add(destinationNode);
                    tempVertices.put(destinationNode.getName(), nodeLayer);

                    // IMPL
                    graph.allVertices.add(destinationNode);
                }
                int edgeWeight = Integer.parseInt(parsedLine.get(i + 1));
                edge = new Edge(node, destinationNode, edgeWeight, lineName);
                node.addEdge(edge);
            }
            if (i - 1 > 0) {
                // then create edge in another direction
                // check does destination already exists
                if (tempVertices.get(parsedLine.get(i - 2)) != null) {
                    Vertex subNode = checkSubnodesAndGetNode(lineName, tempVertices.get(parsedLine.get(i - 2)));
                    if (subNode == null) {
                        destinationNode = new Vertex(parsedLine.get(i - 2));
                        destinationNode.setDefaultLineName(lineName);
                        // TEST
                        tempVertices.get(destinationNode.getName()).add(destinationNode);

                        // IMPL
                        graph.allVertices.add(destinationNode);
                    } else {
                        destinationNode = subNode;
                    }
                } else {
                    destinationNode = new Vertex(parsedLine.get(i - 2));
                    destinationNode.setDefaultLineName(lineName);
                    nodeLayer = new ArrayList<Vertex>();
                    nodeLayer.add(destinationNode);
                    tempVertices.put(destinationNode.getName(), nodeLayer);

                    // IMPL
                    graph.allVertices.add(destinationNode);
                }
                int edgeWeight = Integer.parseInt(parsedLine.get(i - 1));
                edge = new Edge(node, destinationNode, edgeWeight, lineName);
                node.addEdge(edge);
            }
        }
    }

    /**
     * @deprecated NOT USED ANYMORE iterate through nodes - first implementation
     *             without layer handling
     * @param parsedLine
     */
    @SuppressWarnings("unused")
    private void __iterateLines(List<String> parsedLine) {
        String lineName = parsedLine.get(0);
        for (int i = 1; i < parsedLine.size(); i += 2) {
            // iterate each second because from the input format, between is the weight of
            // the edge
            Vertex node = null;
            boolean disabled = false;
            if (graph.getVertex(parsedLine.get(i)) != null) {
                node = graph.getVertex(parsedLine.get(i));
                disabled = true;
            } else {
                node = new Vertex(parsedLine.get(i));
            }
            Edge edge = null;
            Vertex destinationNode = null;
            if (i + 1 < parsedLine.size()) {
                // first create edge in one direction
                // check does destination already exists
                if (graph.getVertex(parsedLine.get(i + 2)) != null) {
                    destinationNode = graph.getVertex(parsedLine.get(i + 2));
                } else {
                    destinationNode = new Vertex(parsedLine.get(i + 2));
                    graph.addVertex(destinationNode);
                }
                edge = new Edge(node, destinationNode, Integer.parseInt(parsedLine.get(i + 1)), lineName);
                node.addEdge(edge);
            }
            if (i - 1 > 0) {
                // then create edge in another direction
                // check does destination already exists
                if (graph.getVertex(parsedLine.get(i - 2)) != null) {
                    destinationNode = graph.getVertex(parsedLine.get(i - 2));
                } else {
                    destinationNode = new Vertex(parsedLine.get(i - 2));
                    graph.addVertex(destinationNode);
                }
                edge = new Edge(node, destinationNode, Integer.parseInt(parsedLine.get(i - 1)), lineName);
                node.addEdge(edge);
            }
            // avoid adding double nodes to map
            if (!disabled) {
                graph.addVertex(node);
            }
        }
    }

    private void setShortestTime(int timeInMinutes) {
        this.shortestTime = timeInMinutes + " min";
    }

    /**
     * @return Formated shortest time
     */
    public String getShortestTime() {
        return this.shortestTime;
    }

    private static ArrayList<String> getParsedString(String inputLine) {
        ArrayList<String> parsedList = new ArrayList<String>();
        // split input line into list using regex rule
        Pattern pattern = Pattern.compile("[^\\s:\"]+|\"[^\"]*\"");
        Matcher matcher = pattern.matcher(inputLine);
        while (matcher.find()) {
            parsedList.add(matcher.group().replaceAll("^\"|\"$", ""));
        }
        return parsedList;
    }
}
