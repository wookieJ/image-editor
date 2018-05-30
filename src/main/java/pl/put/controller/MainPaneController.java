package pl.put.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.opencv.core.Mat;
import pl.put.imageUtils.ImageService;
import pl.put.plugin.Plugin;
import pl.put.plugin.PluginLoader;
import pl.put.propertyUtils.PropertyService;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class MainPaneController {
    @FXML
    private ImageView imageView;

    @FXML
    private HBox pluginsToolBox;

    @FXML
    private Button lineButton;

    @FXML
    private MenuItem polishMenuItem;

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
    private Properties properties;

    @FXML
    public void initialize() {
        loadPlugins();
        loadDefaultProperty();

        initMenu();
        setImageView("sampleAssets\\empty.png");
    }

    private void initMenu() {
        initLoadImage();
    }

    private void initLoadImage() {
        loadImageMenuItem.setOnAction(event -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
            File file = fc.showOpenDialog(new Stage());
            if (file != null && file.exists()) {
                setImageView(file.getPath());
            }
        });
    }

    private void setImageView(String path) {
        ImageService imageService = new ImageService();
        Image image = imageService.convertToImage(imageService.loadMatrix("sampleAssets\\example.png"));
        imageView.setImage(image);
        imageView.setFitWidth(image.getWidth());
        imageView.setFitHeight(image.getHeight());
    }

    private void loadDefaultProperty() {
        PropertyService propertyService = new PropertyService();
        properties = propertyService.loadFromFile("properties\\default.properties");
        updateLabels();
    }

    private void updateLabels() {
        initMenuLabels();
    }

    private void initMenuLabels() {
        fileMenu.setText(properties.getProperty("file-menu", "File"));
        editMenu.setText(properties.getProperty("edit-menu", "Edit"));
        languageMenu.setText(properties.getProperty("language-menu", "Language"));
        closeMenuItem.setText(properties.getProperty("close-menu", "Close"));
    }

    private void loadPlugins() {
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

        createPluginsButtons();
    }

    private void createPluginsButtons() {
        Arrays.stream(plugins).forEach(plugin -> {
            Button pluginButton = new Button(plugin.getName());
            pluginsToolBox.getChildren().add(pluginButton);
            pluginButton.setOnAction(event -> plugin.run(new Mat()));
        });
    }
}