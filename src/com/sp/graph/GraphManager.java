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
     * @param {String} startStation Name of start station
     * @param {String} endStation Name of end station
     * @return {List<String>} List of all station between
     */
    public List<String> getShortestPath(String startStation, String endStation) {
        if (resultData != null) {
            return resultData;
        }
        return null;
    }

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
                    System.out.println("Line: " + lineName + ", Station: " + parsedLine.get(i));
                    if (i + 1 < parsedLine.size()) {
                        System.out.println("Time between " + parsedLine.get(i) + " and " + parsedLine.get(i + 2) + " : "
                                + parsedLine.get(i + 1));
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> getParsedString(String inputLine) {
        ArrayList<String> parsedList = new ArrayList<String>();
        Pattern pattern = Pattern.compile("[^\\s:\"]+|\"[^\"]*\"");
        Matcher matcher = pattern.matcher(inputLine);
        while (matcher.find()) {
            parsedList.add(matcher.group().replaceAll("^\"|\"$", ""));
        }
        return parsedList;
    }

}
