package me.polardyth.polareconomy.utils.config;

import me.polardyth.polareconomy.PolarSettings;
import me.polardyth.polareconomy.utils.config.interfaces.FileHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class ConfigManager implements FileHandler {

    private final String fileName;
    private File file;
    private FileConfiguration fileConfiguration;

    protected ConfigManager(@NotNull String fileName) {
        if (!fileName.endsWith(".yml")) fileName += ".yml";
        this.fileName = fileName;

        save();
        reload();
        save();
    }

    private @NotNull File file() {
        return new File(PolarSettings.getPlugin().getDataFolder(), fileName);
    }

    public FileConfiguration getConfig() { return fileConfiguration; }

    /**
     * Saves any changes to the FileConfiguration to the file on the server
     */
    public void saveFile() {
        if (fileConfiguration == null || file == null) return;
        try { fileConfiguration.save(file); }
        catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Saves the file from the plugin jar to the server (does not override it if it's already on the server)
     */
    public void save() {
        if (file == null) file = file();
        if (file.exists()) return;
        PolarSettings.getPlugin().saveResource(fileName, false);
    }

    /**
     * Updates the FileConfiguration with any changes made to the config file on the server
     */
    public void reload() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file());
    }

    @Override
    public String getFileName() {
        return fileName;
    }

}
