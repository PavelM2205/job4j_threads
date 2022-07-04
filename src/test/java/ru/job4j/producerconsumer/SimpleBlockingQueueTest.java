package ru.job4j.producerconsumer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenAddFourNumbersAndConsumerStartsFirstThenMustBeSame()
            throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(4);
        List<Integer> result = new ArrayList<>();
        Thread producer = new Thread(
                () -> {
                    int i = 1;
                    while (i < 5) {
                        queue.offer(i);
                        i++;
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    int i = 1;
                    while (i < 5) {
                        result.add(queue.poll());
                        i++;
                    }
                }
        );
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
        assertThat(result, is(List.of(1, 2, 3, 4)));
    }

    @Test
    public void whenAddMoreThanSizeThenMustBeSame() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(4);
        List<Integer> result = new ArrayList<>();
        Thread producer = new Thread(
                () -> {
                    int i = 1;
                    while (i < 8) {
                        queue.offer(i);
                        i++;
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    int i = 1;
                    while (i < 8) {
                        result.add(queue.poll());
                        i++;
                    }
                }
        );
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
        assertThat(result, is(List.of(1, 2, 3, 4, 5, 6, 7)));
    }
}