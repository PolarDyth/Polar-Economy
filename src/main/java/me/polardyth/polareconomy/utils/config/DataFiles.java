package me.polardyth.polareconomy.utils.config;

import me.polardyth.polareconomy.utils.config.interfaces.FileHandler;
import me.polardyth.polareconomy.utils.config.interfaces.IDataFiles;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class DataFiles implements IDataFiles {

    private final Map<String, FileHandler> data;

    public DataFiles(String... fileNames) {
        data = new HashMap<>();
        for (String fileName : fileNames) {
            data.put(fileName, new DataManager(fileName));
        }
    }

    @Override
    public FileConfiguration getConfig(String fileName) {
        return data.get(fileName).getConfig();
    }

    @Override
    public void save(String fileName) {
        data.get(fileName).saveFile();
    }
}
