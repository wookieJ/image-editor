package pl.put.plugin;

import org.opencv.core.Mat;

import java.util.Properties;

public abstract class Plugin {
    private String name;
    private String type;

    public abstract Mat run(Mat image);
    public abstract Properties setLanguage();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Plugin(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
