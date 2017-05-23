package com.camon;

import com.google.common.base.Preconditions;
import org.junit.Assert;
import org.junit.Test;

public class PreconditionsTest {

    @Test
    public void exceptionTest() throws Exception {
        // try catch로 감싸는 방법
        Integer a = null;
        Integer b = null;
        boolean success = false;

        try {
            System.out.println(a * b);
        } catch (NullPointerException e) {
            success = true;
        }

        Assert.assertEquals(success, true);
    }

    @Test(expected=NullPointerException.class)
    public void checkNotNullTest() throws Exception {
        // 계산하기 전에 미리 null 체크를 하는 방법
        Integer a = null;
        Integer b = null;
        Preconditions.checkNotNull(a, "must not null");
        Preconditions.checkNotNull(b, "must not null");
        System.out.println(a * b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkArgumentTest() throws Exception {
        // expression 검사
        Integer a = null;
        Integer b = 0;
        Preconditions.checkArgument(a != null, "A must not be null");
        Preconditions.checkArgument(b != null || b > 0, "B must be greater than 0");
        System.out.println(a / b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkArgumentTest2() throws Exception {
        // expression 검사
        Integer a = 100;
        Integer b = 0;
        Preconditions.checkArgument(a != null, "A must not be null");
        Preconditions.checkArgument(b != null && b > 0, "B must be greater than 0");
        System.out.println(a / b);
    }

    @Test
    public void checkArgumentTest3() throws Exception {
        // expression 검사
        Integer a = 100;
        Integer b = 5;
        Preconditions.checkArgument(a != null, "A must not be null");
        Preconditions.checkArgument(b != null && b > 0, "B must be greater than 0");
        int x = a / b;
        System.out.println(x);
        Assert.assertTrue(x == 20);
    }
}

