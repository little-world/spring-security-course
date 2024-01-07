# Streams

### What are Java Streams?

A Stream is a sequence of elements that can be processed in parallel or sequentially. A Stream represents a pipeline of operations that can be performed on a collection of data. The operations are not executed until a terminal operation is called. A Stream can be created from any collection, array, or I/O channel.

### Creating a Stream

There are several ways to create a Stream in Java. Here are a few examples:

```java
// Create a Stream from a List
List<String> list = Arrays.asList("apple", "banana", "cherry");
Stream<String> stream = list.stream();

// Create a Stream from an array
String[] array = { "apple", "banana", "cherry" };
Stream<String> stream = Arrays.stream(array);

// Create a Stream of random numbers
Stream<Integer> stream = Stream.generate(() -> (int) (Math.random() * 100));

// Create a Stream of integers from 1 to 100
IntStream stream = IntStream.range(1, 101);
```
### Intermediate Operations

Intermediate operations are operations that can be performed on a Stream before a terminal operation is called. Here are a few examples:

```java
// Filter the Stream to only include elements that start with "a"
Stream<String> filteredStream = stream.filter(s -> s.startsWith("a"));

// Map the Stream to uppercase
Stream<String> uppercaseStream = stream.map(String::toUpperCase);

// Flatmap the Stream
Stream<String> flatStream = stream.flatMap(s -> Arrays.stream(s.split(",")));

// Sort the Stream
Stream<String> sortedStream = stream.sorted();

```
### Terminal Operations

Terminal operations are operations that are performed on a Stream that trigger the processing of the Stream. Here are a few examples:

```java
// Count the number of elements in the Stream
long count = stream.count();

// Collect the elements in the Stream into a List
List<String> list = stream.collect(Collectors.toList());

// Find the first element in the Stream
Optional<String> firstElement = stream.findFirst();

// Reduce the Stream to a single value
int sum = stream.reduce(0, (a, b) -> a + b);
```
### Parallel Processing

Java Streams can be processed in parallel, which can improve performance on multi-core systems. To process a Stream in parallel, simply call the parallel() method:

```java
// Create a parallel Stream from a List
List<String> list = Arrays.asList("apple", "banana", "cherry");
Stream<String> parallelStream = list.parallelStream();
```

### Streams are lazy
Laziness refers to the concept of delaying the execution of a computation until it is actually needed. This can improve the efficiency of the program by avoiding unnecessary computations.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

Stream<Integer> stream = numbers.stream()
  .filter(n -> n % 2 == 0)
  .map(n -> n * 2);

// At this point, no computation has been done yet.

Integer firstEvenDouble = stream.findFirst().orElse(0);

// Only the first even number that has been doubled is computed.
```
In this example, a list of integers is filtered to only include even numbers, and then each even number is doubled. However, no computation is actually done until the findFirst() terminal operation is called on the stream. At that point, only the first even number that has been doubled is computed. The other even numbers in the list are not computed, since they were not needed. This demonstrates the concept of laziness, where computations are only done when needed, rather than eagerly performing all computations upfront

## Exercises

### Basic Operations:

#### Double each element
Given a list of numbers use a stream to double each element and collect the results into a list.
#### Filter out short strings
From a list of strings filter out strings that are shorter than 5 characters.

### Intermediate Operations:

#### First string with criteria
From a list of strings, find the first string that starts with the letter 'A' and has a length greater than 3.
#### Convert to lengths
Convert a list of strings into a list of their lengths.

### Terminal Operations:

#### Compute the sum
From a list of numbers, compute the sum.

#### Any number greater than 10
Check if a list of numbers has any number greater than 10.

### Advanced Operations:

#### Convert to map
Convert a list of strings into a map where the keys are the strings and the values are their lengths.
#### Partition numbers
Given a list of numbers, partition them into two groups: even and odd.

### Combining Streams and Optional:

#### Find the longest string
From a list of strings, find the longest string. If there's a tie, choose any.
#### Find the maximum value
From a list of integers, find the maximum value.


## Solutions

### Basic Operations:

#### Double each element.
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
List<Integer> doubled = numbers.stream()
 .map(n -> n * 2)
 .collect(Collectors.toList());
System.out.println(doubled);  // [2, 4, 6, 8, 10]
```

#### Filter out short strings.
```java
List<String> words = Arrays.asList("apple", "pie", "banana", "ant");
List<String> longWords = words.stream()
 .filter(w -> w.length() >= 5)
 .collect(Collectors.toList());
System.out.println(longWords);  // [apple, banana]
```

### Intermediate Operations:

#### First string with criteria.
```java
Optional<String> result = words.stream()
 .filter(w -> w.startsWith("A") && w.length() > 3)
 .findFirst();
System.out.println(result.orElse("Not found"));
```

#### Convert to lengths.
```java
List<Integer> lengths = words.stream()
 .map(String::length)
 .collect(Collectors.toList());
System.out.println(lengths);  // [5, 3, 6, 3]
```
### Terminal Operations:

#### Compute the sum.
```java
int sum = numbers.stream()
 .mapToInt(Integer::intValue)
 .sum();
System.out.println(sum);  // 15
```
#### Any number greater than 10.
```java
boolean hasLargeNumber = numbers.stream()
 .anyMatch(n -> n > 10);
System.out.println(hasLargeNumber);  // false
```
### Advanced Operations

#### Convert to map.
```java
Map<String, Integer> wordLengths = words.stream()
 .collect(Collectors.toMap(Function.identity(), String::length));
System.out.println(wordLengths);  // {apple=5, pie=3, banana=6, ant=3}
```
#### Partition numbers.
```java
Map<Boolean, List<Integer>> partitioned = numbers.stream()
.collect(Collectors.partitioningBy(n -> n % 2 == 0));
System.out.println(partitioned);  // {false=[1, 3, 5], true=[2, 4]}
```
#### Combining Streams and Optional:

#### Find the longest string.
```java
Optional<String> longest = words.stream()
 .max(Comparator.comparingInt(String::length));
System.out.println(longest.orElse("Empty list"));
```

#### Find the maximum value.
```java
Optional<Integer> maxValue = numbers.stream()
.max(Integer::compareTo);
System.out.println(maxValue.orElse(null));  // 5
```
