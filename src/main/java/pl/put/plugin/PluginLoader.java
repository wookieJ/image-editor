package pl.put.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginLoader {
    /**
     * Loading plugin main class, by it's name typed in configuration file.
     *
     * @param pluginsDirectory Plugins root directory
     * @param configFileName   Name of plugins configuration file
     * @return Loaded class of plugin
     * @throws IOException            Throwing while file loading process
     * @throws ClassNotFoundException Throwing when there is no class
     */
    public static Class<?> loadClass(File pluginsDirectory, String configFileName) throws IOException, ClassNotFoundException {
        final JarFile jarFile = new JarFile(pluginsDirectory);
        final JarEntry jarEntry = jarFile.getJarEntry(configFileName);
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(jarEntry)));
        final HashMap<String, String> data = new HashMap<>();

        String in;
        while ((in = bufferedReader.readLine()) != null) {
            if (in.isEmpty() || in.startsWith("#"))
                continue;
            final String[] split = in.split(" ");
            data.put(split[0], split[1]);
        }

        jarFile.close();

        return Class.forName(data.get("Main"), true, new URLClassLoader(new URL[]{pluginsDirectory.toURI().toURL()}));
    }

    /**
     * Loading plugin available in directory
     *
     * @param pluginsDirectory plugins root directory
     * @param configFileName   Name of plugins configuration file
     * @return Array of classes in plugins root directory available
     * @throws IOException            Throwing while file loading process
     * @throws ClassNotFoundException Throwing when there is no class
     */
    public static Class<?>[] loadPlugins(String pluginsDirectory, String configFileName) throws IOException, ClassNotFoundException {
        return loadPlugins(new File(pluginsDirectory), configFileName);
    }

    private static Class<?>[] loadPlugins(File dir, String config) throws IOException, ClassNotFoundException {
        final File[] files = dir.listFiles();
        final Class<?>[] classes = new Class<?>[files.length];
        for (int i = 0; i < files.length; i++) {
            classes[i] = loadClass(files[i], config);
        }

        return classes;
    }

    /**
     * Getting an array of plugins in root directory file
     *
     * @param classes Plugins classes we want to convert into plugin objects
     * @return array of plugins
     * @throws InstantiationException throwing while creaating new instance of plugin based on class
     * @throws IllegalAccessException throwing while creaating new instance of plugin based on class
     */
    public static Plugin[] initAsPlugin(Class<?>[] classes) throws InstantiationException, IllegalAccessException {
        final Plugin[] plugins = new Plugin[classes.length];
        for (int i = 0; i < classes.length; i++) {
            plugins[i] = initAsPlugin(classes[i]);
        }

        return plugins;
    }

    private static Plugin initAsPlugin(Class<?> group) throws InstantiationException, IllegalAccessException {
        return (Plugin) group.newInstance();
    }
}
