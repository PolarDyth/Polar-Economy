package me.polardyth.polareconomy.utils.config;

import me.polardyth.polareconomy.PolarSettings;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

class DataManager implements FileHandler {

    private final String fileName;
    private final File folder;
    private File file;
    private FileConfiguration fileConfiguration;

    protected DataManager(@NotNull String fileName) {
        if (!fileName.endsWith(".yml")) fileName += ".yml";
        this.fileName = fileName;
        folder = new File(PolarSettings.getPlugin().getDataFolder(), "data");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        save();
        reload();
        save();
    }

    private @NotNull File file() {
        return new File(folder, fileName);
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
        PolarSettings.getPlugin().saveResource("data/" + fileName, false);
    }

    /**
     * Updates the FileConfiguration with any changes made to the config file on the server
     */
    public void reload() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file());
    }

    public String getFileName() {
        return fileName;
    }

}