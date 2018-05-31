package pl.put.model;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.util.LinkedList;
import java.util.List;

public class CanvasHistory {
    private LinkedList<Mat> history;
    int iterator;

    public int getIterator() {
        return iterator;
    }

    public void setIterator(int iterator) {
        this.iterator = iterator;
    }

    public List<Mat> getHistory() {
        return history;
    }

    public CanvasHistory() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        this.history = new LinkedList<>();
        setIterator(-1);
    }

    /**
     * Adding position to the history
     *
     * @param matrix matrix in history
     */
    public void addToHistory(Mat matrix) {
        history.add(matrix);
        iterator++;
    }

    /**
     * Getting the newest position in history
     *
     * @return last position in history
     */
    public Mat getLast() {
        iterator = history.size() - 1;
        return history.getLast();
    }

    /**
     * Getting next position in history. If there is no next position it returns actual
     *
     * @return next position in history
     */
    public Mat getNext() {
        if (iterator + 1 < history.size()) {
            iterator++;
        }
        return history.get(iterator);
    }

    /**
     * Getting previous position in history. If the is no previous position it returns actual
     *
     * @return previous position in history
     */
    public Mat getPrevious() {
        if (iterator - 1 >= 0) {
            iterator--;
        }
        return history.get(iterator);
    }

    /**
     * Checking if history is empty
     *
     * @return true if history is empty, false otherwise
     */
    public boolean isEmpty() {
        return history.isEmpty();
    }

    /**
     * Checking if iterator points at last element
     *
     * @return true if iterator points at last element, false otherwise
     */
    public boolean isIteratorAtLast() {
        return getIterator() == history.size() - 1;
    }

    /**
     * Deleting elements which occur after iterator
     */
    public void deleteAfterIterator() {
        while (getIterator() + 1< history.size()) {
            history.pollLast();
        }
    }
}
