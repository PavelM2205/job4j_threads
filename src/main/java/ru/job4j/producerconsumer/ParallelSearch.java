package ru.job4j.producerconsumer;

public class ParallelSearch {
    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        final Thread consumer = new Thread(
                () -> {
                    try {
                        while (true) {
                            System.out.println(queue.poll());
                        }
                    } catch (InterruptedException exc) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        Thread producer = new Thread(
                () -> {
                    try {
                        for (int index = 0; index != 3; index++) {
                            queue.offer(index);
                            Thread.sleep(500);
                        }
                        consumer.interrupt();
                    } catch (InterruptedException exc) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        consumer.start();
        producer.start();
        producer.join();
        consumer.join();
    }
}
