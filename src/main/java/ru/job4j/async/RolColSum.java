package ru.job4j.async;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        long start = System.currentTimeMillis();
        int n = matrix.length;
        Sums[] result = new Sums[n];
        int rowSum = 0;
        int colSum = 0;
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                rowSum += matrix[row][column];
            }
            result[row] = new Sums(rowSum, 0);
            rowSum = 0;
        }
        for (int column = 0; column < n; column++) {
            for (int row = 0; row < n; row++) {
                colSum += matrix[row][column];
            }
            result[column].setColSum(colSum);
            colSum = 0;
        }
        System.out.println("Время работы синхронного метода: " + (System.currentTimeMillis() - start));
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        Map<Integer, CompletableFuture<Sums>> tasks = new HashMap<>();
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            tasks.put(i, getTask(matrix, i));
        }
        for (Integer key : tasks.keySet()) {
            result[key] = tasks.get(key).get();
        }
        System.out.println("Время работы асинхронного метода: " + (System.currentTimeMillis() - start));
        return result;
    }

    private static CompletableFuture<Sums> getTask(int[][] matrix, int rowColumnNumber) {
        return CompletableFuture.supplyAsync(
                () -> {
                    int rowSum = 0;
                    int colSum = 0;
                    for (int column = 0; column < matrix.length; column++) {
                        rowSum += matrix[rowColumnNumber][column];
                    }

                    for (int row = 0; row < matrix.length; row++) {
                        colSum += matrix[row][rowColumnNumber];
                    }
                    return new Sums(rowSum, colSum);
                }
        );
    }

    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            return "Sums{" + "rowSum="
                    + rowSum + ", colSum="
                    + colSum + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }
}
