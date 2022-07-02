package ru.job4j.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SimpleFileFileSaver implements FileSaver {

    @Override
    public void save(String content, File file) {
        try {
        Files.writeString(file.toPath(), content);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
