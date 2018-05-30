package pl.put.imageUtils

import javafx.scene.image.Image
import org.opencv.core.Mat
import spock.lang.Specification
import spock.lang.Unroll

class ImageServiceTest extends Specification {
    @Unroll
    def "loadMatrix(#filePath).width() == #width"() {
        expect:
        ImageService imageService = new ImageService()
        imageService.loadMatrix(filePath) != null
        imageService.loadMatrix(filePath).width() == width

        where:
        filePath                    | width
        "sampleAssets\\example.png" | 512
    }

    @Unroll
    def "loadMatrix(#filePath).height() == #height"() {
        expect:
        ImageService imageService = new ImageService()
        imageService.loadMatrix(filePath) != null
        imageService.loadMatrix(filePath).height() == height

        where:
        filePath                    | height
        "sampleAssets\\example.png" | 512
    }

    @Unroll
    def "convertToImage(#filePath).width() == #width"() {
        ImageService imageService = new ImageService()

        expect:
        Mat matrix = imageService.loadMatrix(filePath)
        imageService.convertToImage(matrix).width == width

        where:
        filePath                    | width
        "sampleAssets\\example.png" | 512
    }

    @Unroll
    def "convertToImage(#filePath).height() == #height"() {
        ImageService imageService = new ImageService()

        expect:
        Mat matrix = imageService.loadMatrix(filePath)
        imageService.convertToImage(matrix).height == height

        where:
        filePath                    | height
        "sampleAssets\\example.png" | 512
    }
}
