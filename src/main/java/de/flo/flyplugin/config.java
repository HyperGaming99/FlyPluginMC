package de.flo.flyplugin;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class config {

    private static File file = new File(FlyPlugin.getInstance().getDataFolder(), "config.yml");

    private static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public static void setDefaults() {
        cfg.options().copyDefaults(true);
        cfg.addDefault("Allgemein.prefix", "§f§lFly§b§lSystem §8→ ");
        saveFile();
    }

    public static String getString(String path) {
        return cfg.getString(path).replaceAll("&", "§");
    }
    public static void saveFile() {
        try {
            cfg.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}