package me.polardyth.polareconomy.utils.files;

import me.polardyth.polareconomy.utils.files.interfaces.FileHandler;

import java.util.HashMap;
import java.util.Map;

public class DataFiles {

    private final Map<String, FileHandler> data;

    public DataFiles(String... fileNames) {
        data = new HashMap<>();
        for (String fileName : fileNames) {
            data.put(fileName, new DataManager(fileName));
        }
    }

    public FileHandler getFile(String fileName) {
        return data.get(fileName);
    }
}
