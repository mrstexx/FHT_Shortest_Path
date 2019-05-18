package com.sp.graph;

import java.util.Arrays;

/**
 * Implementation of min heap
 */
public class Heap {

    private Vertex[] heap;
    private int maxSize;
    private int currentSize = 0;

    public Heap(int numberOfPlaces) {
        maxSize = numberOfPlaces; // end of the heap
        heap = new Vertex[maxSize + 1];
        Arrays.fill(heap, null);
    }

    public void insert(Vertex node, int shortestTime) {
        node.setVertexWeight(shortestTime);
        currentSize++;
        int i = currentSize; // last index
        if (heap[i / 2] != null) {
            while (i > 1 && (heap[i / 2].getVertexWeight() < shortestTime)) { // swapping
                heap[i] = heap[i / 2]; // set parent element
                i = i / 2; // get parent index
            }
        }
        heap[i] = node;
    }

    public Vertex remove() {
        int i = 1, j;
        Vertex element = heap[1];
        if (currentSize > 0) {
            do {
                j = 2 * i;
                if (j < currentSize) {
                    if (heap[j] != null && heap[j + 1] != null) {
                        if (heap[j].getVertexWeight() < heap[j + 1].getVertexWeight()) {
                            j++;
                        }
                    }
                    if (heap[currentSize] != null && heap[j] != null) {
                        if (heap[currentSize].getVertexWeight() < heap[j].getVertexWeight()) {
                            heap[i] = heap[j];
                            i = j;
                        } else {
                            heap[i] = heap[currentSize];
                        }
                    }
                } else {
                    heap[i] = heap[currentSize];
                }
            } while (heap[i].getVertexWeight() != heap[currentSize].getVertexWeight());
            currentSize--;
            return element;
        }
        return null;
    }

}
