package com.sp.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphManager {

    private Graph graph;
    private String shortestTime;

    public GraphManager() {
        graph = new Graph();
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
        // NOTE Temp here for debugging to have overview of created edges between
        // vertices
        graph.printAll();
    }

    private void iterateLines(List<String> parsedLine) {
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
