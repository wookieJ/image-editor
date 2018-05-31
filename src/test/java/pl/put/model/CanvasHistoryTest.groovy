package pl.put.model

import org.opencv.core.Mat
import org.opencv.core.Size
import spock.lang.Specification

class CanvasHistoryTest extends Specification {
    def "Creating history"() {
        when:
        CanvasHistory canvasHistory = new CanvasHistory()

        then:
        canvasHistory.getIterator() == -1
        canvasHistory.isEmpty() == true
    }

    def "Adding new history"() {
        when:
        CanvasHistory canvasHistory = new CanvasHistory()
        Mat exampleMatrix = new Mat(new Size(10, 10), 0)
        canvasHistory.addToHistory(exampleMatrix)

        then:
        canvasHistory.getIterator() == 0
        canvasHistory.isEmpty() == false
        canvasHistory.history.size() == 1

        and:
        canvasHistory.addToHistory(exampleMatrix)

        then:
        canvasHistory.getIterator() == 1
        canvasHistory.isEmpty() == false
        canvasHistory.history.size() == 2
    }

    def "Getting last position"() {
        when:
        CanvasHistory canvasHistory = new CanvasHistory()
        Mat exampleMatrix = new Mat(new Size(10, 10), 0)
        canvasHistory.addToHistory(exampleMatrix)
        canvasHistory.addToHistory(exampleMatrix)
        Mat lastMat = new Mat(new Size(15, 15), 1)
        canvasHistory.addToHistory(lastMat)
        Mat lastMatTest = canvasHistory.getLast()

        then:
        canvasHistory.getIterator() == 2
        canvasHistory.isEmpty() == false
        canvasHistory.history.size() == 3

        assert lastMatTest == lastMat
    }

    def "Getting next position"() {
        when:
        CanvasHistory canvasHistory = new CanvasHistory()
        Mat exampleMatrix = new Mat(new Size(10, 10), 0)
        canvasHistory.addToHistory(exampleMatrix)
        canvasHistory.addToHistory(exampleMatrix)
        Mat lastMat = new Mat(new Size(15, 15), 1)
        canvasHistory.addToHistory(lastMat)
        canvasHistory.iterator = 0
        Mat lastMatTest = canvasHistory.getNext()

        then:
        lastMatTest == exampleMatrix
        canvasHistory.getIterator() == 1
        canvasHistory.history.size() == 3
    }

    def "Getting next repeated position"() {
        when:
        CanvasHistory canvasHistory = new CanvasHistory()
        Mat exampleMatrix = new Mat(new Size(10, 10), 0)
        canvasHistory.addToHistory(exampleMatrix)
        canvasHistory.addToHistory(exampleMatrix)
        Mat lastMat = new Mat(new Size(15, 15), 1)
        canvasHistory.addToHistory(lastMat)
        Mat lastMatTest = canvasHistory.getNext()

        then:
        lastMatTest == lastMat
        canvasHistory.getIterator() == 2
    }

    def "Getting previous position"() {
        when:
        CanvasHistory canvasHistory = new CanvasHistory()
        Mat exampleMatrix = new Mat(new Size(10, 10), 0)
        canvasHistory.addToHistory(exampleMatrix)
        canvasHistory.addToHistory(exampleMatrix)
        Mat lastMat = new Mat(new Size(15, 15), 1)
        canvasHistory.addToHistory(lastMat)
        Mat lastMatTest = canvasHistory.getPrevious()

        then:
        lastMatTest == exampleMatrix
        canvasHistory.getIterator() == 1
        canvasHistory.history.size() == 3
    }

    def "Getting previous repeated position"() {
        when:
        CanvasHistory canvasHistory = new CanvasHistory()
        Mat exampleMatrix = new Mat(new Size(10, 10), 0)
        canvasHistory.addToHistory(exampleMatrix)
        Mat lastMat = new Mat(new Size(15, 15), 1)
        canvasHistory.addToHistory(lastMat)
        Mat lastMatTest = canvasHistory.getLast()

        then:
        lastMatTest == lastMat
        canvasHistory.getIterator() == 1
        canvasHistory.history.size() == 2

        when:
        lastMatTest = canvasHistory.getPrevious()

        then:
        lastMatTest == exampleMatrix
        canvasHistory.getIterator() == 0
        canvasHistory.history.size() == 2

        when:
        lastMatTest = canvasHistory.getPrevious()

        then:
        lastMatTest == exampleMatrix
        canvasHistory.getIterator() == 0
        canvasHistory.history.size() == 2
    }

    def "isIteratorAtLast"() {
        when:
        CanvasHistory canvasHistory = new CanvasHistory()

        then:
        canvasHistory.isIteratorAtLast() == true

        when:
        Mat exampleMatrix = new Mat(new Size(10, 10), 0)
        canvasHistory.addToHistory(exampleMatrix)
        canvasHistory.addToHistory(exampleMatrix)

        then:
        canvasHistory.isIteratorAtLast() == true

        when:
        canvasHistory.getPrevious()

        then:
        canvasHistory.isIteratorAtLast() == false

        when:
        canvasHistory.getPrevious()
        canvasHistory.getNext()

        then:
        canvasHistory.isIteratorAtLast() == true
    }

    def "deleteAfterIterator"() {
        when:
        CanvasHistory canvasHistory = new CanvasHistory()
        Mat exampleMatrix = new Mat(new Size(10, 10), 0)
        canvasHistory.addToHistory(exampleMatrix)
        canvasHistory.addToHistory(exampleMatrix)
        canvasHistory.addToHistory(exampleMatrix)
        canvasHistory.addToHistory(exampleMatrix)
        canvasHistory.getPrevious()

        then:
        canvasHistory.isIteratorAtLast() == false
        canvasHistory.history.size() == 4
        canvasHistory.getIterator() == 2

        and:
        canvasHistory.deleteAfterIterator()

        then:
        canvasHistory.isIteratorAtLast() == true
        canvasHistory.history.size() == 3
        canvasHistory.getIterator() == 2

        and:
        canvasHistory.getPrevious()
        canvasHistory.getPrevious()
        canvasHistory.deleteAfterIterator()

        then:
        canvasHistory.history.size() == 1
        canvasHistory.getIterator() == 0
    }
}