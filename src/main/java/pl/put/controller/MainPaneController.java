package pl.put.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import pl.put.plugin.Plugin;
import pl.put.plugin.PluginLoader;

import java.io.IOException;
import java.util.Arrays;

public class MainPaneController {
    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private ImageView imageView;

    @FXML
    private Menu helpMenu;

    @FXML
    private Button lineButton;

    @FXML
    private Label rightLabel;

    @FXML
    private Button openButton;

    @FXML
    private Menu editMenu;

    @FXML
    private Label leftLabel;

    @FXML
    private Button bottomCancelButton;

    @FXML
    private Button redoButton;

    @FXML
    private MenuItem englishMenuItem;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private Menu languageMenu;

    @FXML
    private ProgressBar bottomProgressBar;

    @FXML
    private Button rectangleButton;

    @FXML
    private MenuItem loadImageMenuItem;

    @FXML
    private Menu fileMenu;

    @FXML
    private Button undoButton;

    private Plugin[] plugins;

    @FXML
    public void initialize() {
        System.out.println("Starting loading plugins...");

        // loading plugins
        plugins = null;

        try {
            plugins = PluginLoader.initAsPlugin(PluginLoader.loadPlugins("plugins", "config.cfg"));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Arrays.stream(plugins).forEach(plugin -> {
            System.out.println("Plugin running");
            plugin.run();
            
//            Button button = new Button(plugin.run());
//            gridPane.add (button, 1, i );
//            button.setOnAction();

            plugin.close();
        });

        System.out.println("END");
    }
}