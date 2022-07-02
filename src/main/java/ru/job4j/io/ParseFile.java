package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;
    private final FileSaver fileSaver;

    public ParseFile(File file, FileSaver fileSaver) {
        this.file = file;
        this.fileSaver = fileSaver;
    }

    public synchronized String getContent() throws IOException {
        return parseContent(ch -> true);
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        return parseContent(ch -> ch < 0X80);
    }

    private String parseContent(Predicate<Character> condition) throws IOException {
        StringBuilder result = new StringBuilder();
        try (InputStream in = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buffer = new byte[8192];
            int readData;
            while ((readData = in.read(buffer, 0, buffer.length)) != -1) {
                for (byte el : buffer) {
                    if (condition.test((char) el)) {
                        result.append((char) el);
                    }
                }
            }
        }
        return result.toString();
    }

    public synchronized void saveContent(String content) {
        fileSaver.save(content, file);
    }
}
