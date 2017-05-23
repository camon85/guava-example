package com.camon;

import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PrimitiveTest {

    @Test
    public void arrayTest() throws Exception {
        int[] intArray = {1, 2, 3, 4, 5, 6, 7};

        List<Integer> integers = Ints.asList(intArray);
        System.out.println(integers.toString()); // [1, 2, 3, 4, 5, 6, 7]

        intArray = Ints.toArray(integers);
        System.out.println(Arrays.toString(intArray)); // [1, 2, 3, 4, 5, 6, 7]

        boolean contains = Ints.contains(intArray, 3);
        assertTrue(contains);
        assertEquals(1, Ints.min(intArray));
        assertEquals(7, Ints.max(intArray));
    }

}
