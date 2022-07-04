package ru.job4j.producerconsumer;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenFetchAllThenThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    try {
                        for (int i = 0; i < 5; i++) {
                            queue.offer(i);
                        }
                    } catch (InterruptedException exc) {
                        Thread.currentThread().interrupt();
                    }

                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    try {
                        while ((queue.size() != 0) || !Thread.currentThread().isInterrupted()) {
                            buffer.add(queue.poll());
                        }
                    } catch (InterruptedException exc) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }

    @Test
    public void whenAddMoreThanQueSizeThenMustGetAll() throws InterruptedException {
        CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(4);
        Thread producer = new Thread(
                () -> {
                    try {
                        for (int i = 0; i < 7; i++) {
                            queue.offer(i);
                        }
                    } catch (InterruptedException exc) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    try {
                        while ((queue.size() != 0) || !Thread.currentThread().isInterrupted()) {
                            buffer.add(queue.poll());
                        }
                    } catch (InterruptedException exc) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(List.of(0, 1, 2, 3, 4, 5, 6)));
    }
}