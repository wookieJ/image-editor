package pl.put.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import pl.put.plugin.Plugin;
import pl.put.plugin.PluginLoader;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class MainPaneController {
    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private ImageView imageView;

    @FXML
    private HBox pluginsToolBox;

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

        // Adding plugins to toolbar
        Arrays.stream(plugins).filter(plugin -> plugin.getType().toLowerCase().contains("image")).forEach(plugin -> {
            Button pluginButton = new Button(plugin.getName());
            pluginsToolBox.getChildren().add(pluginButton);
//            pluginButton.setOnAction(event -> plugin.run());
        });
        Arrays.stream(plugins).filter(plugin -> plugin.getType().toLowerCase().contains("language")).forEach(plugin -> {
            Properties properties = plugin.setLanguage();
        });
    }
}