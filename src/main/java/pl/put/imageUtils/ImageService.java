package pl.put.imageUtils;

import javafx.scene.image.Image;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.ByteArrayInputStream;

public class ImageService {
    /**
     * Loading matrix from file
     *
     * @param filePath path we want to get matrix from
     * @return matrix based on file
     */
    public Mat loadMatrix(String filePath) {
        Imgcodecs imageCodecs = new Imgcodecs();
        Mat matrix = imageCodecs.imread(filePath);
        return matrix;
    }

    /**
     * Converting matrix to imge object
     *
     * @param matrix matrix we want to convert
     * @return converted image
     */
    public Image convertToImage(Mat matrix) {

        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", matrix, buffer);
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }

    public ImageService() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
}
