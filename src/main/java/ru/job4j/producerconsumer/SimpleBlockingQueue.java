package ru.job4j.producerconsumer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int size;
    private int count = 0;

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public synchronized void offer(T value) {
        while (count >= size) {
            try {
                this.wait();
            } catch (InterruptedException exc) {
                Thread.currentThread().interrupt();
            }
        }
        queue.offer(value);
        count++;
        this.notify();
    }

    public synchronized T poll() {
        while (count == 0) {
            try {
                this.wait();
            } catch (InterruptedException exc) {
                Thread.currentThread().interrupt();
            }
        }
        T result = queue.poll();
        count--;
        this.notify();
        return result;
    }
}
