package pl.put;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = new File("src/main/java/pl/put/view/MainPane.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Image Processing");
        primaryStage.setScene(new Scene(root, 1200.0, 770.0));
        primaryStage.getIcons().add(new Image("assets/logo.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static int max(int a, int b) {
        System.out.println(a);
        System.out.println(b);
        return a >= b ? a : b;
    }
}
