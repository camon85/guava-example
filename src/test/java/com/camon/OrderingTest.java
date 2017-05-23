package com.camon;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class OrderingTest {

  @Test
  public void withNullTest() throws Exception {
    List<Integer> numbers = Arrays.asList(1, 3, 5, null, 2, 4);
    Collections.sort(numbers, Ordering.natural().nullsFirst());
    System.out.println(numbers); // [null, 1, 2, 3, 4, 5]

    assertThat(numbers.get(0), nullValue());
  }

  @Test
  public void customOrderTest() throws Exception {
    List<Person> devTeam = new ArrayList<>();
    devTeam.add(new Person(1, "jooyong", "migum"));
    devTeam.add(new Person(2, "devhak", "imae"));
    devTeam.add(new Person(3, "cjr", "bokjeong"));

    Ordering<Person> byName = new Ordering<Person>() {
      @Override
      public int compare(Person left, Person right) {
        return Ordering.natural().compare(left.getName(), right.getName());
      }
    };

    Collections.sort(devTeam, byName);
    System.out.println(devTeam); // [Person{id=3, name='cjr', address='bokjeong'}, Person{id=2, name='devhak', address='imae'}, Person{id=1, name='jooyong', address='migum'}]

    Collections.sort(devTeam, byName.reverse());
    System.out.println(devTeam); // [Person{id=1, name='jooyong', address='migum'}, Person{id=2, name='devhak', address='imae'}, Person{id=3, name='cjr', address='bokjeong'}]
  }

  @Test
  public void onResultOfTest() throws Exception {
    List<Person> devTeam = new ArrayList<>();
    devTeam.add(new Person(1, "jooyong", "migum"));
    devTeam.add(new Person(2, "devhak", "imae"));
    devTeam.add(new Person(3, "cjr", "bokjeong"));

    Function<Person, Integer> lengthFunction = new Function<Person, Integer>() {
      @Override
      public Integer apply(Person input) {
        return input.getName().length();
      }
    };

    Ordering<Person> byLength = Ordering.natural().onResultOf(lengthFunction);
    Collections.sort(devTeam, byLength);
    System.out.println(devTeam); // [Person{id=3, name='cjr', address='bokjeong'}, Person{id=2, name='devhak', address='imae'}, Person{id=1, name='jooyong', address='migum'}]
  }

  @Test
  public void sortedCopyTest() throws Exception {
    List<Person> devTeam = new ArrayList<>();
    devTeam.add(new Person(1, "jooyong", "migum"));
    devTeam.add(new Person(2, "devhak", "imae"));
    devTeam.add(new Person(3, "cjr", "bokjeong"));

    Ordering<Person> byName = new Ordering<Person>() {
      @Override
      public int compare(Person left, Person right) {
        return Ordering.natural().compare(left.getName(), right.getName());
      }
    };

    List<Person> sortedTeam = byName.sortedCopy(devTeam);

    System.out.println(devTeam); // [Person{id=1, name='jooyong', address='migum'}, Person{id=2, name='devhak', address='imae'}, Person{id=3, name='cjr', address='bokjeong'}]
    System.out.println(sortedTeam); // [Person{id=3, name='cjr', address='bokjeong'}, Person{id=2, name='devhak', address='imae'}, Person{id=1, name='jooyong', address='migum'}]
  }

  @Test
  public void leastOfTest() throws Exception {
    List<Person> devTeam = new ArrayList<>();
    devTeam.add(new Person(1, "jooyong", "migum"));
    devTeam.add(new Person(2, "devhak", "imae"));
    devTeam.add(new Person(3, "cjr", "bokjeong"));

    Ordering<Person> byName = new Ordering<Person>() {
      @Override
      public int compare(Person left, Person right) {
        return Ordering.natural().compare(left.getName(), right.getName());
      }
    };

    List<Person> leastOfTeam = byName.leastOf(devTeam, 2);
    System.out.println(devTeam); // [Person{id=1, name='jooyong', address='migum'}, Person{id=2, name='devhak', address='imae'}, Person{id=3, name='cjr', address='bokjeong'}]
    System.out.println(leastOfTeam); // [Person{id=3, name='cjr', address='bokjeong'}, Person{id=2, name='devhak', address='imae'}]
    assertEquals(2, leastOfTeam.size());
  }

}
