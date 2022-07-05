package ru.job4j.cache;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CacheTest {

    @Test
    public void whenAddOneElementThenGetSame() {
        Cache cache = new Cache();
        Base test = new Base(1, 1);
        test.setName("test");
        cache.add(test);
        assertThat(cache.get(test.getId()), is(test));
    }

    @Test
    public void whenAddTwoAndDeleteOneThenMustBeOne() {
        Cache cache = new Cache();
        Base test1 = new Base(1, 1);
        test1.setName("test1");
        Base test2 = new Base(2, 1);
        test1.setName("test2");
        cache.add(test1);
        cache.add(test2);
        cache.delete(test1);
        assertNull(cache.get(test1.getId()));
        assertThat(cache.get(test2.getId()), is(test2));
    }

    @Test
    public void whenUpdateElementWithSameVersionThenElementMustBeChangedAndVersionIncremented() {
        Cache cache = new Cache();
        Base test1 = new Base(1, 1);
        test1.setName("test1");
        cache.add(test1);
        test1.setName("changed");
        cache.update(test1);
        assertThat(cache.get(test1.getId()).getName(), is(test1.getName()));
        assertThat(cache.get(test1.getId()).getVersion(), is(2));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateElementWithDifferentVersionAndSameIDThenMustBeException() {
        Cache cache = new Cache();
        Base test1 = new Base(1, 1);
        test1.setName("test1");
        Base changed = new Base(1, 2);
        changed.setName("changed");
        cache.add(test1);
        cache.update(changed);
    }
}