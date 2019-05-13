package com.sp.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphHelper {

    private static List<String> resultData;

    public static List<String> getShortestPath(String fileName) {
        importNewData(fileName);
        if (resultData != null) {
            return resultData;
        }
        return null;
    }

    @SuppressWarnings("resource")
    private static void importNewData(String fileName) {
        File file = new File(fileName);
        try {
            BufferedReader bufferReader = new BufferedReader(new FileReader(file));
            resultData = new ArrayList<>();
            String line = "";
            while ((line = bufferReader.readLine()) != null) {
                resultData.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
