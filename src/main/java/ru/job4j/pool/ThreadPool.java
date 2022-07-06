package ru.job4j.pool;

import ru.job4j.producerconsumer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            threads.add(new PoolThread(tasks));
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (var thread : threads) {
            thread.interrupt();
        }
    }

    private static class PoolThread extends Thread {
        private final SimpleBlockingQueue<Runnable> queue;

        public PoolThread(SimpleBlockingQueue<Runnable> queue) {
            this.queue = queue;
            this.start();
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    queue.poll().run();
                } catch (InterruptedException exc) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool();
        Runnable task1 = () -> System.out.println("task1");
        Runnable task2 = () -> System.out.println("task2");
        Runnable task3 = () -> System.out.println("task3");
        try {
            pool.work(task1);
            pool.work(task2);
            pool.work(task3);
        } catch (InterruptedException exc) {
            Thread.currentThread().interrupt();
        }
        pool.shutdown();
    }
}
