package ru.job4j.forkjoin;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SearcherTest {

    @Test
    public void whenMassiveLengthLessThanTenThenMustBeLineSearch() {
        Integer[] mas = {8, 5, 3, 4, 12, 46, 7};
        Integer expected = 6;
        assertThat(Searcher.search(mas, 7), is(expected));
    }

    @Test
    public void whenMassiveMoreThanTenThenMustBeRecursiveSearch() {
        Integer[] mas = {8, 5, 3, 4, 12, 46, 7, 96, 124, 18, 55, 19};
        Integer expected = 6;
        assertThat(Searcher.search(mas, 7), is(6));
    }

    @Test
    public void whenMassiveHasSeveralMatchesThenMustBeLastIndex() {
        Integer[] mas = {8, 7, 3, 4, 12, 46, 7, 96, 124, 7, 55, 7};
        Integer expected = 11;
        assertThat(Searcher.search(mas, 7), is(11));
    }

    @Test
    public void whenMassiveDoesNotHaveMatchesThenMustBeMinusOne() {
        Integer[] mas = {8, 7, 3, 4, 12, 46, 7, 96, 124, 7, 55, 7};
        Integer expected = -1;
        assertThat(Searcher.search(mas, 1), is(expected));
    }

    @Test
    public void whenMassiveHasAllMatchesThenMustBeLastIndex() {
        Integer[] mas = {7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7};
        Integer expected = 11;
        assertThat(Searcher.search(mas, 7), is(11));
    }
}