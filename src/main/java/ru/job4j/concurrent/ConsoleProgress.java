package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        String[] signs = {"\\", "|", "/"};
        try {
            while (!Thread.currentThread().isInterrupted()) {
                for (var element : signs) {
                    System.out.print("\rLoading... " + element);
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
