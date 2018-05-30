package pl.put.imageUtils;

import javafx.scene.image.Image;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.ByteArrayInputStream;

public class ImageService {
    /**
     * Loading matrix from file
     *
     * @param filePath file path we want to load
     * @return matrix created on file path
     */
    public Mat loadMatrix(String filePath) {
        Imgcodecs imageCodecs = new Imgcodecs();
        Mat matrix = imageCodecs.imread(filePath);

        return matrix;
    }

    /**
     * Converting matrix to image file
     *
     * @param matrix matrix we want to convert
     * @return image from matrix
     */
    public Image convertToImage(Mat matrix) {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", matrix, buffer);
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }
}