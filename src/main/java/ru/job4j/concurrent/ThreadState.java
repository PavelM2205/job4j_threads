package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.println(String.format("first: %s", first.getState()));
            System.out.println(String.format("second: %s", second.getState()));
        }
        System.out.println("The work is completed");
    }
}
