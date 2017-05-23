package com.camon;

import com.google.common.base.Optional;
import org.junit.Assert;
import org.junit.Test;

public class OptionalTest {

    @Test
    public void nullableTest() throws Exception {
        int result1 = sum(null, null);
        int result2 = sum(123, null);
        int result3 = sum(null, 100);
        int result4 = sum(50, 30);

        Assert.assertEquals(result1, 0);
        Assert.assertEquals(result2, 123);
        Assert.assertEquals(result3, 100);
        Assert.assertEquals(result4, 80);
    }

    private int sum(Integer a, Integer b) {
        Optional<Integer> nullableA = Optional.fromNullable(a);
        Optional<Integer> nullableB = Optional.fromNullable(b);
        Integer numA = nullableA.or(0);
        Integer numB = nullableB.or(0);
        return numA + numB;
    }
}
