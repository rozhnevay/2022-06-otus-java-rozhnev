package ru.otus.test;

import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;

public class TestClass {
    public TestClass() {
    }

    @Before
    public void before() {
        System.out.println("Before");
    }

    @Test
    public void test1() {
        System.out.println("Test1");
    }

    @Test
    public void test2() {
        System.out.println("Test2");
    }

    @Test
    public void test3() {
        throw new RuntimeException("Some exception");
    }

    @After
    public void after() {
        System.out.println("After");
    }

}