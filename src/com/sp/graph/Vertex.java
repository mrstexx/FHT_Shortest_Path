package com.sp.graph;

public class Vertex {

    public static final int DEFAULT_TIME = 5;

    private String name;

    public Vertex(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
