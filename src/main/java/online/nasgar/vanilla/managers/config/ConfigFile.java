package online.nasgar.vanilla.managers.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ConfigFile extends YamlConfiguration {

    private final JavaPlugin plugin;
    private final String fileName;
    private final File file;

    public ConfigFile(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.file = new File(plugin.getDataFolder(), fileName);

        this.create();
    }

    public void create() {
        try {
            if (!this.file.exists()) {
                this.file.getParentFile().mkdirs();

                if (this.plugin.getResource(this.fileName) != null) {
                    this.plugin.saveResource(this.fileName, false);
                } else {
                    this.file.createNewFile();
                }
            }
            super.load(file);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void save() {
        try {
            if (!this.file.exists()) {
                this.file.createNewFile();
            }

            super.save(this.file);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void reload() {
        try {
            super.load(file);
            this.save();
        } catch (Exception e) {}
    }
}
