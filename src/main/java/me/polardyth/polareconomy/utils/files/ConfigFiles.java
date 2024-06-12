package me.polardyth.polareconomy.utils.files;

import me.polardyth.polareconomy.utils.files.interfaces.FileHandler;

import java.util.HashMap;
import java.util.Map;

public class ConfigFiles {

    private final Map<String, FileHandler> data;

    public ConfigFiles(String... fileNames) {
        data = new HashMap<>();
        for (String fileName : fileNames) {
            data.put(fileName, new ConfigManager(fileName));
        }
    }

    public FileHandler getFile(String fileName) {
        return data.get(fileName);
    }
}
