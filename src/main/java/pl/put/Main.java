package pl.put;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = new File("src/main/java/pl/put/view/MainPane.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Image Processing");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {

//        // loading plugins
//        Plugin[] plugins = null;
//        try {
//            Class<?>[] classes = PluginLoader.loadPlugins("plugins", "config.cfg");
//            plugins = PluginLoader.initAsPlugin(classes);
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        Arrays.stream(plugins).forEach(plugin -> {
//            System.out.println("Plugin running");
//            plugin.run();
//            plugin.close();
//        });
        launch(args);
    }
}
