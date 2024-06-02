package me.polardyth.polareconomy.utils.config;

import me.polardyth.polareconomy.utils.config.interfaces.FileHandler;
import me.polardyth.polareconomy.utils.config.interfaces.IConfigFiles;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class ConfigFiles implements IConfigFiles {

    private final Map<String, FileHandler> data;

    public ConfigFiles(String... fileNames) {
        data = new HashMap<>();
        for (String fileName : fileNames) {
            data.put(fileName, new ConfigManager(fileName));
        }
    }

    @Override
    public FileHandler getFile(String fileName) {
        return data.get(fileName);
    }
}
