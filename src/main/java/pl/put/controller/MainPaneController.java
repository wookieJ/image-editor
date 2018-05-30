package pl.put.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private ToggleButton lineButton;

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
    private ToggleButton rectangleButton;

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
        initToolbox();
        initImageView();
        setImageView("sampleAssets\\empty.png");
    }

    private void initImageView() {
        imageView.setOnMousePressed(e -> {
            if (lineButton.isSelected())
                writeCoordinates(e);
//            if(rectangleButton.isSelected())
//                drawRectangle(e);
        });
    }

    private void writeCoordinates(MouseEvent e) {
        System.out.println(e.getSceneX());
        System.out.println(e.getSceneY());
    }

    private void initToolbox() {
        openButton.setOnAction(e -> setImageViewFromFile());
        // TODO - undo
        // TODO - redo
        lineButton.setOnAction(e -> rectangleButton.setSelected(false));
        rectangleButton.setOnAction(e -> lineButton.setSelected(false));
    }

    private void initMenu() {
        initLoadImageMenu();
        initCloseMenu();
        initEditMenu();
    }

    private void initEditMenu() {
        initEnglishMenuItem();
        initPolishMenuItem();
    }

    private void initPolishMenuItem() {
        polishMenuItem.setOnAction(e -> {
            PropertyService propertyService = new PropertyService();
            properties = propertyService.loadFromFile("properties\\polish.properties");
            updateLabels();
        });
    }

    private void initEnglishMenuItem() {
        englishMenuItem.setOnAction(e -> {
            PropertyService propertyService = new PropertyService();
            properties = propertyService.loadFromFile("properties\\english.properties");
            updateLabels();
        });
    }

    private void initCloseMenu() {
        closeMenuItem.setOnAction(e -> System.exit(0));
    }

    // TODO - problem with polish signs in path
    private void initLoadImageMenu() {
        loadImageMenuItem.setOnAction(event -> {
            setImageViewFromFile();
        });
    }

    private void setImageViewFromFile() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        File file = fc.showOpenDialog(new Stage());
        if (file != null && file.exists()) {
            setImageView(file.getAbsolutePath());
        }
    }

    private void setImageView(String path) {
        ImageService imageService = new ImageService();
        Image image = imageService.convertToImage(imageService.loadMatrix(path));
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