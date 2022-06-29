package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;

public class WgetWithTime implements Runnable {
    private final String url;
    private final int speed;

    public WgetWithTime(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream("./data/test.txt")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
                long difference = System.currentTimeMillis() - start;
                long longSpeed = speed * 1000L;
                long pause = difference < longSpeed ? longSpeed - difference : 0;
                Thread.sleep(pause);
                start = System.currentTimeMillis();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new WgetWithTime(url, speed));
        wget.start();
        wget.join();
    }
}
