package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class WgetWithTime implements Runnable {
    private final String url;
    private final int speed;
    private final String targetDirectory;
    private static final int BUFFER_SIZE = 1024;
    private static final long MILLISECONDS_IN_SECOND = 1000;

    public WgetWithTime(String url, int speed, String targetDirectory) {
        this.url = url;
        this.speed = speed;
        this.targetDirectory = targetDirectory;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream(String.format("%s/%s", targetDirectory, Path.of(url).getFileName()))) {
            byte[] dataBuffer = new byte[BUFFER_SIZE];
            int bytesRead;
            int downloadData = 0;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, BUFFER_SIZE)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    long difference = System.currentTimeMillis() - start;
                    long pause = difference < MILLISECONDS_IN_SECOND ? MILLISECONDS_IN_SECOND - difference : 0;
                    Thread.sleep(pause);
                    downloadData = 0;
                    start = System.currentTimeMillis();
                }
            }
        } catch (Exception exc) {
        exc.printStackTrace();
        }
    }

    private static void checkArguments(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Wrong input. Use: URL SPEED TARGET_FOLDER");
        }

        Path path = Path.of(args[2]);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException(String.format("Folder %s isn't exist.", args[2]));
        }

        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException(String.format("File %s isn't a folder.", args[2]));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        checkArguments(args);
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new WgetWithTime(args[0], speed, args[2]));
        wget.start();
        wget.join();
    }
}
