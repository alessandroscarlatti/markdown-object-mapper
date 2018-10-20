package com.calculator

import com.scarlatti.MarkdownMapper
import org.junit.Test

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Saturday, 10/20/2018
 */
class CalculatorTest {

    @Test
    void addition() {
        int val = new Calculator().add(1, 2)
        assert val == 3
    }

    @Test
    void testUsingResourcesUsingCodeBlock() {
        TestData test = new MarkdownMapper().readResourceAs("/com/calculator/test1.md", TestData.class)
        int result = new Calculator().add(test.val1, test.val2)
        assert test.val1 != 0
        assert result == test.result
    }

    @Test
    void testUsingResourcesUsingParagraph() {
        TestData test = new MarkdownMapper().readResourceAs("/com/calculator/test2.md", TestData.class)
        int result = new Calculator().add(test.val1, test.val2)
        assert test.val1 != 0
        assert result == test.result
    }

    static class TestData {
        int val1
        int val2
        int result
    }
}
