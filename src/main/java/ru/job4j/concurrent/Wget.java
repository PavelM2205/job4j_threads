package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread load = new Thread(
                () -> {
                    try {
                        for (int index = 0; index <= 100; index++) {
                            System.out.print("\rLoading : " + index + " %");
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException exc) {
                        exc.printStackTrace();
                    }
                }
        );
        load.start();
    }
}
