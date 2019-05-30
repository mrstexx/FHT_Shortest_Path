package com.sp.graph;

import java.util.ArrayList;

/**
 * Implementation of min heap
 */
public class Heap {
    private ArrayList<Vertex> heap;

    public Heap() {
        heap = new ArrayList<>();
    }

    private int parentNode(int i) {
        // if i is already a root node
        if (i == 0) {
            return 0;
        }
        // position of parent node
        return (i - 1) / 2;
    }

    private int leftNode(int i) {
        // position of left node
        return (2 * i + 1);
    }

    private int rightNode(int i) {
        // position of the right node
        return (2 * i + 2);
    }

    private void swap(int x, int y) {
        // swap values at two indexes
        Vertex temp = heap.get(x);
        heap.set(x, heap.get(y));
        heap.set(y, temp);
    }

    private void heapifyDown(int i) {
        int left = leftNode(i);
        int right = rightNode(i);

        int smallest = i;

        // compare heap value at 'i' with its left and right child node and find
        // smallest value
        if (left < size() && heap.get(left).getDistance() < heap.get(i).getDistance()) {
            smallest = left;
        }
        if (right < size() && heap.get(right).getDistance() < heap.get(smallest).getDistance()) {
            smallest = right;
        }
        if (smallest != i) {
            // swap with child having smaller value
            swap(i, smallest);
            // call heapifyDown on the child
            heapifyDown(smallest);
        }
    }

    private void heapifyUp(int i) {
        if (i > 0 && heap.get(parentNode(i)).getDistance() > heap.get(i).getDistance()) {
            // swap the two if heap property is violated
            swap(i, parentNode(i));
            heapifyUp(parentNode(i));
        }
    }

    public int size() {
        return heap.size();
    }

    public Boolean isEmpty() {
        return heap.isEmpty();
    }

    // insert specified vertex into the heap
    public void put(Vertex node, int distance) {
        node.setDistance(distance);
        heap.add(node);
        int index = size() - 1;
        heapifyUp(index);
    }

    public Vertex get() {
        try {
            // if heap is empty, throw an exception
            if (size() == 0) {
                throw new Exception("Index out of range(Heap underflow)");
            }
            Vertex root = heap.get(0);
            // replace root of the heap with the last element of the list
            heap.set(0, heap.get(size() - 1));
            heap.remove(size() - 1);

            // call heapifyDown on root node
            heapifyDown(0);

            return root;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    public void clear() {
        System.out.print("Removing heap: ");
        while (!heap.isEmpty()) {
            System.out.print(get() + " ");
        }
        System.out.println();
    }

    // returns true if heap contains the specified element
    public boolean contains(Vertex node) {
        return heap.contains(node);
    }
}