package com.english

import com.scarlatti.MarkdownMapper
import org.junit.Test

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Saturday, 10/20/2018
 */
class UpperCaserTest {


    @Test
    void testSingleUpperCaser() {
        TestData testData = new MarkdownMapper().readResourceAs("/com/english/test1.md", TestData.class)
        String result = new UpperCaser().convertToUpperCase(testData.original)
        assert testData.original != null
        assert result == testData.expectedResult
    }

    @Test
    void testManyUpperCasers() {

        List<TestData> testDatas = [
                new MarkdownMapper().readResourceAs("/com/english/test2.md", TestData.class),
                new MarkdownMapper().readResourceAs("/com/english/test3.md", TestData.class),
                new MarkdownMapper().readResourceAs("/com/english/test4.md", TestData.class),
                new MarkdownMapper().readResourceAs("/com/english/test5.md", TestData.class),
        ]

        for (TestData testData : testDatas) {
            String result = new UpperCaser().convertToUpperCase(testData.original)
            assert testData.original != null
            assert result == testData.expectedResult
        }
    }

    static class TestData {
        String original
        String expectedResult
    }
}
