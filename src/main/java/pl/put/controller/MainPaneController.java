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

import java.io.File;
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

    Properties properties;
    private Plugin[] plugins;

    @FXML
    public void initialize() {
        loadPlugins();
        initMenuLabels();
        initMenu();

        setImageView("sampleAssets\\empty.png");
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

        createButtonsPlugins();
        createLanguagesPlugins();
    }

    private void createLanguagesPlugins() {
        Arrays.stream(plugins).filter(plugin -> plugin.getType().toLowerCase().contains("language")).forEach(plugin -> {
            this.properties = plugin.setLanguage();
        });
    }

    private void createButtonsPlugins() {
        Arrays.stream(plugins).filter(plugin -> plugin.getType().toLowerCase().contains("image")).forEach(plugin -> {
            Button pluginButton = new Button(plugin.getName());
            pluginsToolBox.getChildren().add(pluginButton);
//            pluginButton.setOnAction(event -> plugin.run());
        });
    }

    private void initMenuLabels() {
//        fileMenu.setText(properties.getProperty("file-menu", "Plik"));
//        editMenu.setText(properties.getProperty("edit-menu", "Edycja"));
//        languageMenu.setText(properties.getProperty("language-menu", "Język"));
//        helpMenu.setText(properties.getProperty("help-menu", "Pomoc"));
//        aboutMenuItem.setText(properties.getProperty("about-menuItem", "O aplikacji"));
//        closeMenuItem.setText(properties.getProperty("close-menuItem", "Zamknij"));
    }

    private void initMenu() {
        initLoadImageMenuItem();
        initCloseMenutItem();
//        initLanguageMenuItem();
    }

    private void initLoadImageMenuItem() {
        loadImageMenuItem.setOnAction(event -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Png", "*.png"));
            File file = fc.showOpenDialog(new Stage());
            if (file != null && file.exists()) {
                setImageView(file.toPath().toString());
            }
        });
    }

    // TODO - polish signs in source problem
    private void setImageView(String filePath) {
        if (filePath.matches("[^ęóąśłżźćń]")) {
            ImageService imageService = new ImageService();
            Mat matrix = imageService.loadMatrix(filePath);
            Image image = imageService.convertToImage(matrix);
            imageView.setImage(image);
            imageView.setFitHeight(image.getHeight());
            imageView.setFitWidth(image.getWidth());
        }
    }

    private void initCloseMenutItem() {
        closeMenuItem.setOnAction(event -> System.exit(0));
    }
}