package ru.job4j.cas;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CASCountTest {

    @Test
    public void whenTwoThreadsInvokeIncrementThenGetReturnEight()
            throws InterruptedException {
        CASCount counter = new CASCount();
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 4; i++) {
                        counter.increment();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 4; i++) {
                        counter.increment();
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(counter.get(), is(8));
    }
}