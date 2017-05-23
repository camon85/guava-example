package com.camon;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.*;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CollectionTest {

    @Test
    public void filterContainsTest() throws Exception {
        List<String> languages = Arrays.asList("java", "scala", "python", "javascript", "go");
        Iterable<String> filteredLanguages = Iterables.filter(languages, Predicates.containsPattern("o"));
        System.out.println(filteredLanguages); // [python, go]

        assertThat(filteredLanguages, containsInAnyOrder("python", "go"));
    }

    @Test
    public void filterNotTest() throws Exception {
        List<String> languages = Lists.newArrayList("java", "scala", "python", "javascript", "go");
        Predicate<CharSequence> containsPredicate = Predicates.containsPattern("o");
        Collection<String> filteredLanguages = Collections2.filter(languages, Predicates.not(containsPredicate));
        System.out.println(filteredLanguages); // [java, scala, javascript]

        assertThat(filteredLanguages, containsInAnyOrder("java", "scala", "javascript"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void collections2Test() throws Exception {
        List<String> languages = Arrays.asList("java", "scala", "python", "javascript", "go");
        Collection<String> filteredLanguages = Collections2.filter(languages, Predicates.containsPattern("o"));
        System.out.println(filteredLanguages); // [python, go]

        // Iterables.filter를 사용하면 size를 알 수 없지만 Collections2.filter는 collection객체가 리턴되기 때문에 size도 알아낼 수 있다.
        assertEquals(2, filteredLanguages.size());
        assertThat(filteredLanguages, containsInAnyOrder("python", "go"));

        // Arrays.asList()는 불변 객체를 리턴하기 때문에 오류 발생
        filteredLanguages.add("kotlin");
    }

    @Test(expected = IllegalArgumentException.class)
    public void collections2AddTest() throws Exception {
        List<String> languages = Lists.newArrayList("java", "scala", "python", "javascript", "go");
        Collection<String> filteredLanguages = Collections2.filter(languages, Predicates.containsPattern("o"));
        System.out.println(filteredLanguages); // [python, go]

        assertEquals(2, filteredLanguages.size());
        assertThat(filteredLanguages, containsInAnyOrder("python", "go"));

        // 필터 결과에 맞지 않는 값을 넣을 수 없다.
        filteredLanguages.add("haskel");
    }

    @Test
    public void collections2AddTest2() {
        List<String> languages = Lists.newArrayList("java", "scala", "python", "javascript", "go");
        Collection<String> filteredLanguages = Collections2.filter(languages, Predicates.containsPattern("o"));

        assertEquals(2, filteredLanguages.size());
        assertThat(filteredLanguages, containsInAnyOrder("python", "go"));

        // 원본 collection 객체에도 add 된다는 점에 주의!
        // Collections2.filter()의 리턴되는 값은 FilteredCollection이고, 기존 객체를 그대로 사용하고 predicate를 적용해서 보여준다.
        filteredLanguages.add("kotlin");

        System.out.println(languages); // [java, scala, python, javascript, go, kotlin]
        System.out.println(filteredLanguages); // [python, go, kotlin]

        assertEquals(6, languages.size());
        assertEquals(3, filteredLanguages.size());
    }

    @Test
    public void customFilterTest() throws Exception {
        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return input.contains("o");
            }
        };

        List<String> languages = Lists.newArrayList("java", "scala", "python", "javascript", "go");
        Collection<String> filteredLanguages = Collections2.filter(languages, predicate);

        assertEquals(2, filteredLanguages.size());
        assertThat(filteredLanguages, containsInAnyOrder("python", "go"));
    }

    @Test
    public void filterOrTest() throws Exception {
        List<String> languages = Lists.newArrayList("java", "scala", "python", "javascript", "go");
        Predicate<CharSequence> oPredicate = Predicates.containsPattern("o");
        Predicate<CharSequence> jPredicate = Predicates.containsPattern("j");
        Predicate<CharSequence> sPredicate = Predicates.containsPattern("s");

        // 2개 이상의 Predicate를 결합 할 수 있다.
        Collection<String> filteredLanguages = Collections2.filter(languages, Predicates.or(oPredicate, jPredicate, sPredicate));
        System.out.println(filteredLanguages); // [java, scala, python, javascript, go]

        assertThat(filteredLanguages, containsInAnyOrder("java", "scala", "python", "javascript", "go"));
    }

    @Test
    public void filterAndTest() throws Exception {
        List<String> languages = Lists.newArrayList("java", "scala", "python", "javascript", "go");
        Predicate<CharSequence> oPredicate = Predicates.containsPattern("o");
        Predicate<CharSequence> jPredicate = Predicates.containsPattern("j");

        Collection<String> filteredLanguages = Collections2.filter(languages, Predicates.and(oPredicate, jPredicate));
        System.out.println(filteredLanguages); // []

        assertEquals(0, filteredLanguages.size());
    }

    @Test
    public void filterNotNullTest() throws Exception {
        List<String> languages = Lists.newArrayList("java", "scala", null, "python", "javascript", "go", null);
        assertEquals(7, languages.size());

        // null 제거
        Collection<String> filteredLanguages = Collections2.filter(languages, Predicates.<String>notNull());
        System.out.println(filteredLanguages); // [java, scala, python, javascript, go]

        assertEquals(5, filteredLanguages.size());
    }

    @Test
    public void iterablesAllTest() throws Exception {
        List<String> languages = Lists.newArrayList("java", "scala", "python", "javascript", "go");

        // 모두 일치하는지
        boolean constainsAllJOrSOrO = Iterables.all(languages, Predicates.containsPattern("j|s|o"));
        assertTrue(constainsAllJOrSOrO);

        boolean containsAllO = Iterables.all(languages, Predicates.containsPattern("o"));
        assertFalse(containsAllO);
    }

    @Test
    public void iterablesAnyTest() throws Exception {
        List<String> languages = Lists.newArrayList("java", "scala", "python", "javascript", "go");

        // 일치하는 것이 하나라도 있는지
        boolean containsAnyO = Iterables.any(languages, Predicates.containsPattern("o"));
        assertTrue(containsAnyO);
    }

    @Test
    public void iterablesTest() throws Exception {
        List<String> languages = Lists.newArrayList("java", "scala", "python", "javascript", "go");

        // "java"가 몇개 있는지 확인
        int javaFrequency = Iterables.frequency(languages, "java");
        assertEquals(1, javaFrequency);

        // 최대 2개까지 제한하여 리턴
        Iterable<String> limit = Iterables.limit(languages, 2);
        assertThat(limit, containsInAnyOrder("java", "scala"));

        // collection 2개 이상을 합친다
        Iterable<String> concat = Iterables.concat(languages, Lists.newArrayList("maven", "gradle"));
        assertThat(concat, containsInAnyOrder("java", "scala", "python", "javascript", "go", "maven", "gradle"));
    }

    @Test
    public void transformTest() throws Exception {
        List<Person> devTeam = new ArrayList<>();
        devTeam.add(new Person(1, "jooyong", "migum"));
        devTeam.add(new Person(2, "devhak", "imae"));
        devTeam.add(new Person(3, "cjr", "bokjeong"));

        Function<Person, Integer> f = new Function<Person, Integer>() {
            @Override
            public Integer apply(Person input) {
                return input.getId();
            }
        };

        // id 뽑아내기
        List<Integer> ids = Lists.transform(devTeam, f);
        System.out.println(ids); // [1, 2, 3]

        assertEquals(1, ids.get(0).intValue());
    }

    @Test
    public void listToMap() throws Exception {
        List<Person> devTeam = new ArrayList<>();
        devTeam.add(new Person(1, "jooyong", "migum"));
        devTeam.add(new Person(2, "devhak", "imae"));
        devTeam.add(new Person(3, "cjr", "bokjeong"));

        Function<Person, Integer> f = new Function<Person, Integer>() {
            @Override
            public Integer apply(Person input) {
                return input.getId();
            }
        };

        ImmutableMap<Integer, Person> personImmutableMap = Maps.uniqueIndex(devTeam, f);
        System.out.println(personImmutableMap); // {1=Person{id=1, name='jooyong', address='migum'}, 2=Person{id=2, name='devhak', address='imae'}, 3=Person{id=3, name='cjr', address='bokjeong'}}

        assertEquals("jooyong", personImmutableMap.get(1).getName());
    }

    @Test
    public void listToListMap() throws Exception {
        List<Person> devTeam = new ArrayList<>();
        devTeam.add(new Person(1, "jooyong", "migum"));
        devTeam.add(new Person(2, "devhak", "imae"));
        devTeam.add(new Person(3, "cjr", "bokjeong"));

        Function<Person, HashMap<String, Integer>> f = new Function<Person, HashMap<String, Integer>>() {
            @Override
            public HashMap<String, Integer> apply(Person input) {
                HashMap<String, Integer> stringLongHashMap = new HashMap<>();
                stringLongHashMap.put("id", input.getId());
                return stringLongHashMap;
            }
        };

        List<HashMap<String, Integer>> transform = Lists.transform(devTeam, f);
        System.out.println(transform); // [{id=1}, {id=2}, {id=3}]
    }

}
