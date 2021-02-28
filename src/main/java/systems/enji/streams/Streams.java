package systems.enji.streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Spliterator;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

public class Streams {

  public static void main(String[] args) {

    /* stream sources */
    Stream<String> s = sourcesDemo();

    /* intermediate operations */
    s = intermediatesDemo(s);

    /* terminal operations */
    terminalsDemo(s);

    /* Collectors */
    collectorsDemo(s);

    /* Reductions */
    reductionsDemo(s);

  }

  private static Stream<String> sourcesDemo() {

    List<String> list = List.of("skipme_too", "har", "harrrr", "one", "har_single", "x", "two", "three", "three");
    String[] array = new String[] { "a", "b" };

    // List.stream:
    Stream<String> s = list.stream();

    // Arrays.stream:
    // Stream<String> s = Arrays.stream(stringArray);

    // Stream.of:
    // Stream<String> s = Stream.of("a", "b");
    // Stream<String> s = Stream.of(list); // DOES NOT WORK!
    // Stream<String> s = Stream.of(array);

    // stream builder:
    // Stream.Builder<String> b = Stream.builder();
    // Stream<String> s = b.add("a").add("b").build();

    // "almost" infinite stream:
    // Stream<String> s = Stream.iterate("a", e -> e.length() < 5, e -> e + "a");

    // infinite streams:
    // Stream<String> s = Stream.iterate("a", e -> e + "a");
    // Stream<String> s = Stream.generate(() -> UUID.randomUUID().toString());
    // List<Boolean> infiniteRandomBooleans = infiniteRandomBooleans();

    return s;

  }

  /**
   * Simple intermediate operations.
   */
  private static Stream<String> intermediatesDemo(Stream<String> s) {
    s = s.skip(1);
    s = s.dropWhile(e -> e.contains("har"));
    // s = s.takeWhile(e -> e.contains("har"));
    s = s.filter(e -> !e.contains("x"));
    s = s.map(e -> e.toUpperCase());
    // s = s.peek(e -> System.out.println("peeking: " + e));
    s = s.distinct();
    s = s.flatMap(e -> Stream.of(e, String.valueOf(e.length())));
    s = s.limit(10);
    s = s.sorted();
    return s;
  }

  /**
   * Simple terminal operations (without Collectors and reductions).
   */
  private static void terminalsDemo(Stream<String> s) {
    s.forEach(System.out::println);
    // s.forEachOrdered(System.out::println);
    // System.out.println(s.allMatch(e -> e.contains("T")));
    // System.out.println(s.anyMatch(e -> e.contains("EE")));
    // System.out.println(s.noneMatch(e -> e.contains("EE")));
    // System.out.println(s.count());
    // System.out.println(s.findAny().toString());
    // System.out.println(s.findFirst().toString());
    // System.out.println(s.max((a, b) -> a.compareTo(b)).get());
    // System.out.println(s.min((a, b) -> a.compareTo(b)).get());
  }

  /**
   * Collectors (terminal operations).
   */
  private static void collectorsDemo(Stream<String> s) {

    // System.out.println(s.collect(Collectors.toList()));
    // System.out.println(s.collect(Collectors.toSet())); // duplicates are removed

    // toMap (see also: groupingBy below)
    // System.out.println(s.distinct().collect(Collectors.toMap(e -> e, e -> e.length()))); // duplicate keys lead to an error
    // System.out.println(s.collect(Collectors.toMap(e -> e, e -> e, (a, b) -> "" + a + "|" + b))); // values for duplicate keys are merged
    // System.out.println(s.collect(Collectors.toMap(e -> e, e -> e, (a, b) -> "" + a + "|" + b, LinkedHashMap::new))); // last param provides the map to fill

    // joining
    // System.out.println(s.collect(Collectors.joining())); // concatenation without delimiter
    // System.out.println(s.collect(Collectors.joining("|"))); // concatenation with delimiter
    // System.out.println(s.collect(Collectors.joining("|", "PRE-", "-POST"))); // concatenation with delimiter, prefix and suffix

    // averaging*
    // System.out.println(s.collect(Collectors.averagingInt(e -> e.length())));
    // System.out.println(s.collect(Collectors.averagingLong(e -> (long) e.length())));
    // System.out.println(s.collect(Collectors.averagingDouble(e -> (double) e.length())));

    // groupingBy
    // System.out.println(s.collect(Collectors.groupingBy(e -> e.length()))); // grouped elements are put into a list
    // System.out.println(s.collect(Collectors.groupingBy(e -> e.length(), Collectors.joining()))); // grouped elements are processed by the given Collector
    // System.out.println(s.collect(Collectors.groupingBy(e -> e.length(), TreeMap::new, Collectors.joining()))); // second param provides the map to fill

    // partitioningBy: simpler than groupingBy because there are only two keys (true and false)
    // System.out.println(s.collect(Collectors.partitioningBy(e -> e.length() > 3))); // grouped elements are put into a list
    // System.out.println(s.collect(Collectors.partitioningBy(e -> e.length() > 3, Collectors.joining()))); // grouped elements are processed by the given Collector

    // functions similar to stream.*; useful in combination with Collectors.groupingBy / partitioningBy (where stream.* is not available)
    // System.out.println(s.collect(Collectors.counting()));
    // System.out.println(s.collect(Collectors.filtering(e -> e.length() > 3, Collectors.toList())));
    // System.out.println(s.collect(Collectors.mapping(String::length, Collectors.toList())));
    // System.out.println(s.collect(Collectors.flatMapping(e -> Stream.of(e), Collectors.toList())));
    // System.out.println(s.collect(Collectors.reducing((a,b) -> a + "|" + b)).get());

    // summarizing
    // System.out.println(s.collect(Collectors.summarizingInt(e -> e.length())));
    // System.out.println(s.collect(Collectors.summarizingLong(e -> (long) e.length())));
    // System.out.println(s.collect(Collectors.summarizingDouble(e -> (double) e.length())));

    // summing (subset of summarizing)
    // System.out.println(s.collect(Collectors.summingInt(e -> e.length())));
    // System.out.println(s.collect(Collectors.summingLong(e -> (long) e.length())));
    // System.out.println(s.collect(Collectors.summingDouble(e -> (double) e.length())));

  }

  private static void reductionsDemo(Stream<String> s) {

    // reduction without explicit identity element (i.e., first step of reduction is equal to first stream element)
    // System.out.println(s.reduce((a, b) -> a + "," + b).get());

    // reduction with explicit identity element (and hence, not an Optional return)
    // System.out.println(s.reduce("START", (a, b) -> a + "," + b));

    // reduction with an additional combiner / aggregator; TODO: difference to previous method unknown
    // System.out.println(s.reduce("START", (a, b) -> a + "," + b, (a, b) -> a + "|" + b));

  }

  /**
   * Generates a (potentially) infinite list of random booleans.
   */
  private static List<Boolean> infiniteRandomBooleans() {
    var randomBooleans = Stream.
        // to improve performance, you can also create the Random once before:
        generate(() -> new Random().nextBoolean())
        // without the following limit, you would get an unlimited stream;
        // however, turning an unlimited stream into a list would take forever...
        .limit(10)
        .collect(Collectors.toList());
    return randomBooleans;
  }

}
