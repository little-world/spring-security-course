# Optional

Optional is a container object that may or may not contain a non-null value. By using Optional, you can better deal with cases that might lead to NullPointerException. Instead of returning null, methods can return an Optional to indicate that results might be absent.

#### Creating Optional

```java
Optional<String> full = Optional.of("Hello");
Optional<String> empty = Optional.empty();
Optional<String> nullable = Optional.ofNullable(null);
```
#### Checking value presence:

- isPresent(): If a value is present, returns true, otherwise false.
- isEmpty(): If a value is absent, returns true, otherwise false (since Java 11).

```java
if (full.isPresent()) 
  System.out.println("Value found!");
```

#### Retrieving the value:

- `get()`: If a value is present, returns the value, otherwise throws NoSuchElementException.
- `orElse(T other)`: Returns the value if present, otherwise returns the specified other value.
- `orElseGet(Supplier<? extends T> other)`: Returns the value if present, otherwise returns the result produced by the supplying function.
- `orElseThrow()`: Returns the value if present, otherwise throws NoSuchElementException.

```java
String value = full.orElse("Default Value");
```
#### Conditional operations:

- `ifPresent(Consumer<? super T> consumer)`: If a value is present, it invokes the specified consumer with the value, otherwise does nothing.
- `ifPresentOrElse(Consumer<? super T> action`, Runnable emptyAction): If a value is present, performs the given action with the value, otherwise performs the given empty-based action (since Java 9).

```java
full.ifPresent(v -> System.out.println("Value is: " + v));
```

#### Transforming Optional values:

- `map()`: If a value is present, returns an Optional describing the result of applying the given function to the value, otherwise returns an empty Optional.
- `flatMap()`: If a value is present, returns the result of applying the given Optional-bearing mapping function to the value, otherwise returns an empty Optional.

```java
Optional<Integer> length = full.map(String::length);
```

#### Filtering values:

```java
Optional<String> longString = full.filter(v -> v.length() > 10);
```

#### Use Cases:

- Optional can be used as a better return type for methods that might not always return a value.
- It's useful for chaining methods to transform or check data in a null-safe manner.
- It's a clear API design indicating the possibility of an absent value, which makes your code safer and more readable.

#### Best Practices:

- Don't use Optional for class fields or method parameters. It's designed as a return type.
- Always provide alternative actions for empty Optional values to prevent unexpected errors.
- Don't excessively chain operations on Optional, as it can make the code hard to read.


## Exercises

- Basic Creation:

-- Create an Optional that contains a string "Hello".
-- Create an empty Optional.
-- Create an Optional from a nullable string variable. Test it with both a null and non-null value.

-Retrieving and Checking Values:

-- Check if the Optional from 1a has a value. Print out the result.
-- Retrieve the value of the Optional from 1a. If it's not present, provide a default value "World".
-- Use orElseThrow to retrieve the value of the Optional from 1b, throwing an exception if it's not present.

- Transformations:

-- Transform the Optional from 1a to contain its length using the map function.
-- Transform the string inside the Optional from 1a to an Optional of its lowercase version using flatMap.

- Conditional Operations:

-- If the Optional from 1a has a value, print it out using ifPresent.
-- Filter the Optional from 1a to check if the string length is greater than 5. If it is, print out "Long String", otherwise print "Short String".

- Chaining Operations:

-- Using the Optional from 1c (with non-null value), transform the string to uppercase, then check if it starts with the letter 'H', and finally, if both conditions are met, print out the transformed string.


## Solutions

Java Optional Solutions:
Basic Creation:

```java
Optional<String> helloOpt = Optional.of("Hello");

Optional<String> emptyOpt = Optional.empty();

String str1 = null;
Optional<String> nullableOpt1 = Optional.ofNullable(str1);

String str2 = "Hi";
Optional<String> nullableOpt2 = Optional.ofNullable(str2);

```

Retrieving and Checking Values:

```java
if (helloOpt.isPresent()) {
System.out.println("Optional has a value!");
} else {
System.out.println("Optional is empty.");
}
```

```java
String valueOrDefault = helloOpt.orElse("World");
System.out.println(valueOrDefault);  // Outputs: Hello
```

```java
String valueOrException = emptyOpt.orElseThrow(() -> new NoSuchElementException("No value present!"));
```

 Transformations:


```java
Optional<Integer> lengthOpt = helloOpt.map(String::length);
System.out.println(lengthOpt.orElse(-1));  // Outputs: 5
```
```java
Optional<String> lowercaseOpt = helloOpt.flatMap(s -> Optional.of(s.toLowerCase()));
System.out.println(lowercaseOpt.orElse("EMPTY"));  // Outputs: hello
```

Conditional Operations:
```java
helloOpt.ifPresent(System.out::println);  // Outputs: Hello
```
```java
String result = helloOpt.map(s -> s.length() > 5 ? "Long String" : "Short String").orElse("Empty Optional");
System.out.println(result);  // Outputs: Short String
```

Chaining Operations:

```java
nullableOpt2
.map(String::toUpperCase)
.filter(s -> s.startsWith("H"))
.ifPresent(System.out::println);  // Outputs: HI
```
