package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int ref;
        int newValue;
        do {
            ref = count.get();
            newValue = ref + 1;
        } while (!count.compareAndSet(ref, newValue));
    }

    public int get() {
        return count.get();
    }
}
