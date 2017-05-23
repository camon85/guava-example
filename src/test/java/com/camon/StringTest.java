package com.camon;

import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringTest {

    @Test(expected = NullPointerException.class)
    public void joinerNullTest() throws Exception {
        List<Integer> integers = Arrays.asList(1, 2, 3, null, 5);
        String joined = Joiner.on(", ")
                .join(integers);
        System.out.println(joined);
    }

    @Test
    public void joinerSkipNullTest() throws Exception {
        List<Integer> integers = Arrays.asList(1, 2, 3, null, 5);
        String joined = Joiner.on(", ")
                .skipNulls()
                .join(integers);
        System.out.println(joined); // 1, 2, 3, 5
    }

    @Test
    public void joinerUseForNullTest() throws Exception {
        List<Integer> integers = Arrays.asList(1, 2, 3, null, 5);
        String joined = Joiner.on(", ")
                .useForNull("empty")
                .join(integers);
        System.out.println(joined); // 1, 2, 3, empty, 5
    }

    @Test
    public void mapJoinerTest() throws Exception {
        // map to String
        Map<Integer, String> integerStringHashMap = new HashMap<>();
        integerStringHashMap.put(1, "one");
        integerStringHashMap.put(2, "two");
        integerStringHashMap.put(3, "three");
        String mapJoiner = Joiner.on("; ")
                .withKeyValueSeparator("->")
                .join(integerStringHashMap);
        System.out.println(mapJoiner); // 1->one; 2->two; 3->three
    }

    @Test
    public void splitTest() throws Exception {
        String sequence = "java ,scala, ,python       ,      go, , javascript";
        Iterable<String> iterable = Splitter.on(',')
                .split(sequence);
        System.out.println(iterable); // [java , scala,  , python       ,       go,  ,  javascript]
    }

    @Test
    public void splitTrimResultTest() throws Exception {
        // 공백문자 제거
        String sequence = "java ,scala, ,python       ,      go, , javascript";
        Iterable<String> iterable = Splitter.on(',')
                .trimResults()
                .split(sequence);
        System.out.println(iterable); // [java, scala, , python, go, , javascript]
    }

    @Test
    public void splitOmitEmptyTest() throws Exception {
        // null 오브젝트 제거
        String sequence = "java ,scala, ,python       ,      go, , javascript";
        Iterable<String> iterable = Splitter.on(',')
                .trimResults()
                .omitEmptyStrings()
                .split(sequence);
        System.out.println(iterable); // [java, scala, python, go, javascript]
    }

    @Test
    public void caseFormatTest() throws Exception {
        String camelString = "myFirstGuava";
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, camelString)); // my-first-guava
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelString)); // my_first_guava
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, camelString)); // MY_FIRST_GUAVA
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, camelString)); // MyFirstGuava

        String underscoreString = "my_first_guava";
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, underscoreString)); // my-first-guava
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_UNDERSCORE, underscoreString)); // MY_FIRST_GUAVA
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, underscoreString)); // MyFirstGuava
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, underscoreString)); // myFirstGuava
    }

    @Test
    public void charMatcherTest() throws Exception {
        System.out.println(CharMatcher.digit().retainFrom("gua1va")); // 1
        System.out.println(CharMatcher.digit().or(CharMatcher.javaLowerCase()).retainFrom("HELLO777guava")); // 777guava
        System.out.println(CharMatcher.whitespace().trimAndCollapseFrom("     my   first  guava ", '_')); // my_first_guava
        System.out.println(CharMatcher.digit().replaceFrom("guava999", "*")); // guava***
    }
}
