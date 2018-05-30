package pl.put.imageUtils

import org.opencv.core.Mat
import spock.lang.Specification
import spock.lang.Unroll

class ImageServiceTest extends Specification {
    @Unroll
    def "System.getProperty(#libraryPath) == #result"() {
        expect:
        System.getProperty(libraryPath) == result

        where:
        libraryPath         | result
        "java.library.path" | "C:\\Software\\opencv-3.4.1\\build\\java\\x64"
    }

    @Unroll
    def "loadMatrix(#filePath).size() == #result"() {
        ImageService imageService = new ImageService();

        expect:
        imageService.loadMatrix(filePath).size().width == width
        imageService.loadMatrix(filePath).size().height == height

        where:
        filePath                           | width | height
        "sampleAssets\\openCV-example.png" | 512   | 512
    }

    @Unroll
    def "convertToImage(#matrix) == result"() {
        ImageService imageService = new ImageService();

        expect:
        Mat mat = imageService.loadMatrix(filePath)
        imageService.convertToImage(mat).width == width
        imageService.convertToImage(mat).height == height

        where:
        filePath                           | width | height
        "sampleAssets\\openCV-example.png" | 512   | 512
    }
}
