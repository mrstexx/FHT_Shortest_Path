package com.sp.graph;

import java.util.Arrays;

/**
 * Implementation of min heap
 */
public class Heap {

    private Vertex[] heap;
    private int maxSize;
    private int heapSize = 0;

    public Heap(int numberOfPlaces) {
        maxSize = numberOfPlaces; // end of the heap
        heap = new Vertex[maxSize + 1];
        Arrays.fill(heap, null);
    }

    public void put(Vertex node, int shortestTime) {
        if (heapSize != maxSize) {
            heapSize++;
            int i = heapSize;
            node.setVertexWeight(shortestTime);
            while (i > 0 && heap[i / 2] != null && heap[i / 2].getVertexWeight() < shortestTime) {
                heap[i] = heap[i / 2];
                i = i / 2;
            }
            heap[i] = node;
        }
    }

    private void heapify() {
    }

    public Vertex get() {
        int i = 1, j;
        Vertex element = heap[i];
        if (heapSize > 0) {
            do {
                j = 2 * i;
                if (j < heapSize) {
                    if (heap[j] != null && heap[j + 1] != null
                            && heap[j].getVertexWeight() < heap[j + 1].getVertexWeight()) {
                        j++;
                    }
                    if (heap[heapSize] != null && heap[j] != null
                            && heap[heapSize].getVertexWeight() < heap[j].getVertexWeight()) {
                        heap[i] = heap[j];
                        i = j;
                    } else {
                        heap[i] = heap[heapSize] != null ? heap[heapSize] : heap[i];
                    }
                } else {
                    heap[i] = heap[heapSize] != null ? heap[heapSize] : heap[i];
                }
            } while (heap[i] != null && heap[heapSize] != null
                    && heap[i].getVertexWeight() != heap[heapSize].getVertexWeight());
            heapSize--;
        }
        System.out.println(heapSize);
        return element;
    }
}
