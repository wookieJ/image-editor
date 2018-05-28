package pl.put.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pl.put.plugin.Plugin;
import pl.put.plugin.PluginLoader;

import java.io.IOException;
import java.util.Arrays;

public class MainPaneController {

    @FXML
    private Button button;

    @FXML
    private Label label;

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
            button.setText(plugin.run());
            plugin.close();
        });

        System.out.println("END");
    }
}