package com.sp.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphManager {

    private Graph graph;
    private List<String> resultData;

    public GraphManager() {
        graph = new Graph();
    }

    /**
     * @param startStation Name of start station
     * @param endStation   Name of end station
     * @return List of all station between
     */
    public List<String> getShortestPath(String startStation, String endStation) {
        if (resultData != null) {
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
                String lineName = parsedLine.get(0);
                for (int i = 1; i < parsedLine.size(); i += 2) {
                    // iterate each second because from the input format, between is the weight of
                    // the edge
                    Vertex node = new Vertex(parsedLine.get(i));
                    Edge edge = null;
                    Vertex destinationNode = null;
                    if (i + 1 < parsedLine.size()) {
                        // first create edge in one direction
                        destinationNode = new Vertex(parsedLine.get(i + 2));
                        edge = new Edge(destinationNode, Integer.parseInt(parsedLine.get(i + 1)), lineName);
                        node.addEdge(edge);
                    }
                    if (i - 1 > 0) {
                        // then create edge in another direction
                        destinationNode = new Vertex(parsedLine.get(i - 2));
                        edge = new Edge(destinationNode, Integer.parseInt(parsedLine.get(i - 1)), lineName);
                        node.addEdge(edge);
                    }
                    graph.addVertex(node);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        graph.printAll();
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
