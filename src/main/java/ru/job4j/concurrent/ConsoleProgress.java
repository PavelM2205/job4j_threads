package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        String[] sign = {"\\", "|", "/"};
        try {
            while (!Thread.currentThread().isInterrupted()) {
                for (int count = 0; count < 3; count++) {
                    System.out.print("\rLoading... " + sign[count]);
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException exc) {
            System.out.print("\rLoading is completed.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}
