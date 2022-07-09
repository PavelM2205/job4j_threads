package ru.job4j.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Searcher<T> extends RecursiveTask<Integer> {
    private static final int MIN_SIZE = 10;
    private final T[] massive;
    private final T target;
    private final int from;
    private final int to;

    public Searcher(T[] massive, int from, int to, T target) {
        this.massive = massive;
        this.from = from;
        this.to = to;
        this.target = target;
    }

    public static <T> Integer search(T[] mas, T target) {
        Searcher<T> searcher = new Searcher<>(mas, 0, mas.length - 1, target);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        return pool.invoke(searcher);
    }

    @Override
    protected Integer compute() {
        Integer result = -1;
        if ((to - from + 1) <= MIN_SIZE) {
            result = lineSearch();
            return result;
        }
        int mid = (from + to) / 2;
        Searcher<T> leftSearcher = new Searcher<>(massive, from, mid, target);
        Searcher<T> rightSearcher = new Searcher<>(massive, mid + 1, to, target);
        leftSearcher.fork();
        rightSearcher.fork();
        Integer left = leftSearcher.join();
        Integer right = rightSearcher.join();
        return Math.max(left, right);
    }

    private Integer lineSearch() {
        Integer result = -1;
        for (int i = from; i <= to; i++) {
            if (target.equals(massive[i])) {
                result = i;
            }
        }
        return result;
    }
}

