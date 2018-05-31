package pl.put.model;

import org.opencv.core.Core;
import org.opencv.core.Mat;

public class Canvas {
    private Mat actualImage;
    private CanvasHistory canvasHistory;

    public CanvasHistory getCanvasHistory() {
        return canvasHistory;
    }

    public Mat getActualImage() {
        return actualImage;
    }

    public void setActualImage(Mat actualImage) {
        this.actualImage = actualImage;
    }

    public Canvas(Mat actualImage) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        this.canvasHistory = new CanvasHistory();
        updateHistory(actualImage);
    }

    /**
     * Adding matrix to history
     *
     * @param matrix matrix we want to add to the history
     */
    public void addToHistory(Mat matrix) {
        canvasHistory.addToHistory(matrix);
    }

    /**
     * Getting previous version
     *
     * @return
     */
    public Mat undo() {
        actualImage = canvasHistory.getPrevious();
        return actualImage;
    }

    /**
     * Getting next version
     *
     * @return
     */
    public Mat redo() {
        actualImage = canvasHistory.getNext();
        return actualImage;
    }

    /**
     * Checking if history of canvas is empty
     *
     * @return true if history is empty
     */
    public boolean isHistoryEmpty() {
        return canvasHistory.isEmpty();
    }

    /**
     * Setting actual matrix and adding previous to history
     *
     * @param matrix
     */
    public void updateHistory(Mat matrix) {
        if (! canvasHistory.isIteratorAtLast()) {
            canvasHistory.deleteAfterIterator();
        }
        setActualImage(matrix);
        addToHistory(actualImage);
    }
}