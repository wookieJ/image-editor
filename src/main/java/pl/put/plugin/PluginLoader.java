package pl.put.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginLoader {
    public static Class<?> loadClass(String dir, String config) throws IOException, ClassNotFoundException {
        return loadClass(new File(dir), config);
    }

    public static Class<?> loadClass(File dir, String config) throws IOException, ClassNotFoundException {
        final JarFile jarFile = new JarFile(dir);
        final JarEntry jarEntry = jarFile.getJarEntry(config);
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

        return Class.forName(data.get("Main"));
    }

    public static Class<?>[] loadPlugins(String dir, String config) throws IOException, ClassNotFoundException {
        return loadPlugins(new File(dir), config);
    }

    public static Class<?>[] loadPlugins(File dir, String config) throws IOException, ClassNotFoundException {
        final File[] files = dir.listFiles();
        final Class<?>[] classes = new Class<?>[files.length];
        for (int i = 0; i < files.length; i++) {
            classes[i] = loadClass(files[i], config);
        }

        return classes;
    }

    public static Plugin initAsPlugin(Class<?> group) throws IllegalAccessException, InstantiationException {
        return (Plugin) group.newInstance();
    }

    public static Plugin[] initAsPlugin(Class<?>[] group) throws InstantiationException, IllegalAccessException {
        final Plugin[] plugins = new Plugin[group.length];
        for (int i = 0; i < group.length; i++) {
            plugins[i] = initAsPlugin(group[i]);
        }

        return plugins;
    }
}
