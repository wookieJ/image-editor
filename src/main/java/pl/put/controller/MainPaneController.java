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
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import pl.put.imageUtils.ImageService;
import pl.put.model.Canvas;
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
    private Point lineBeginPoint;
    private Canvas canvas;

    @FXML
    public void initialize() {
        loadPlugins();
        loadDefaultProperty();

        initMenu();
        initToolbox();
        initImageView();
        setImageViewFromFile("sampleAssets\\empty.png");
    }

    private void initImageView() {
        imageView.setOnMousePressed(e -> {
            if (lineButton.isSelected())
                writeCoordinates(e);
//            if(rectangleButton.isSelected())
//                drawRectangle(e);
        });

        imageView.setOnMouseReleased(e-> {
            if (lineButton.isSelected())
                drawLine(e);
//            if(rectangleButton.isSelected())
//                drawRectangle(e);
        });
    }

    private void drawLine(MouseEvent e) {
        Point lineEndPoint = new Point(e.getX(), e.getY());
        Mat lineMat = canvas.getActualImage().clone();
        Imgproc.line(lineMat, lineBeginPoint, lineEndPoint, new Scalar(0,0,255), 2);
        canvas.updateHistory(lineMat);
        updateImageView();
    }

    private void updateImageView() {
        ImageService imageService = new ImageService();
        imageView.setImage(imageService.convertToImage(canvas.getActualImage()));
    }

    private void writeCoordinates(MouseEvent e) {
        lineBeginPoint = new Point(e.getX(), e.getY());
    }

    private void initToolbox() {
        openButton.setOnAction(e -> setImageViewFromFile());
        undoButton.setOnAction(e-> {
            canvas.undo();
            updateImageView();
        });
        redoButton.setOnAction(e-> {
            canvas.redo();
            updateImageView();
        });
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
            setImageViewFromFile(file.getAbsolutePath());
        }
    }

    private void setImageViewFromFile(String path) {
        ImageService imageService = new ImageService();
        Mat matrix = imageService.loadMatrix(path);
        Image image = imageService.convertToImage(matrix);
        canvas = new Canvas(matrix);
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