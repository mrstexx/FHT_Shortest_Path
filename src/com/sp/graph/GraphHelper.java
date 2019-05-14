package com.sp.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphHelper {

    private static Graph graph;
    private static List<String> resultData;

    /**
     * @param {String} startStation Name of start station
     * @param {String} endStation Name of end station
     * @return {List<String>} List of all station between
     */
    public static List<String> getShortestPath(String startStation, String endStation) {
        if (resultData != null) {
            return resultData;
        }
        return null;
    }

    @SuppressWarnings("resource")
    public static void importNewData(String fileName) {
        File file = new File(fileName);
        try {
            BufferedReader bufferReader = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = bufferReader.readLine()) != null) {
                if (line.equals("")) {
                    continue;
                }
                // TODO import vertices and edges
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
