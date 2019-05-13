package com.sp.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GraphHelper {

    public static String getShortestPath(String fileName) {
        importNewData(fileName);
        return "";
    }

    @SuppressWarnings("resource")
    private static void importNewData(String fileName) {
        File file = new File(fileName);
        try {
            BufferedReader bufferReader = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = bufferReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
