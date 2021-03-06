package ru.job4j.forkjoin;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SearcherTest {

    @Test
    public void whenMassiveLengthLessThanTenThenMustBeLineSearch() {
        Integer[] mas = {8, 5, 3, 4, 12, 46, 7};
        List<Integer> expected = List.of(6);
        assertThat(Searcher.search(mas, 7), is(expected));
    }

    @Test
    public void whenMassiveMoreThanTenThenMustBeRecursiveSearch() {
        Integer[] mas = {8, 5, 3, 4, 12, 46, 7, 96, 124, 18, 55, 19};
        List<Integer> expected = List.of(6);
        assertThat(Searcher.search(mas, 7), is(expected));
    }

    @Test
    public void whenMassiveHasSeveralMatchesThenMustBeLastIndex() {
        Integer[] mas = {8, 7, 3, 4, 12, 46, 7, 96, 124, 7, 55, 7};
        List<Integer> expected = List.of(1, 6, 9, 11);
        assertThat(Searcher.search(mas, 7), is(expected));
    }

    @Test
    public void whenMassiveDoesNotHaveMatchesThenMustBeEmptyList() {
        Integer[] mas = {8, 7, 3, 4, 12, 46, 7, 96, 124, 7, 55, 7};
        List<Integer> expected = List.of();
        assertThat(Searcher.search(mas, 1), is(expected));
    }

    @Test
    public void whenMassiveHasAllMatchesThenMustBeLastIndex() {
        Integer[] mas = {7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7};
        List<Integer> expected = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        assertThat(Searcher.search(mas, 7), is(expected));
    }
}