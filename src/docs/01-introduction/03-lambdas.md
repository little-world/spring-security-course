# Lambdas

Lambda expressions are a new and important feature introduced in Java 8. They allow for clear and concise ways to represent one-method interface using an expression. Lambdas are a form of anonymous function in Java.

### Basic Syntax:
The basic syntax of a lambda is:

```java
(parameter1, parameter2, ...) -> expression
```

```java
(parameter1, parameter2, ...) -> { statements; }
```
# Examples

A lambda expression with no parameters.
```java
() -> System.out.println("Hello World")
```
A lambda expression with one parameter.
```java
x -> x * x
```
A lambda expression with multiple parameters.
```java
(x, y) -> x + y
```

### Functional Interfaces:
In Java, lambda expressions are represented as objects, and so they need a type. The type for a lambda is a functional interface. A functional interface is an interface that has just one abstract method (but can have multiple default or static methods).

Using Java's built-in Runnable functional interface:

```java
Runnable run = () -> System.out.println("Running...");
new Thread(run).start();
```

### Features:
> `Type Inference`:

You don't always need to declare the type of a parameter. The Java compiler can infer it from the context.
```java
// No need to declare 'String s'
Consumer<String> printer = s -> System.out.println(s);
```

> `No Return Keyword`:

If the body of a lambda has a single statement, the Java runtime will return the value of that statement.
```java
Function<Integer, String> converter = (num) -> "Number: " + num;
```

> `Multiple Statements:`

If a lambda has multiple statements, they need to be enclosed in braces.
```java
BinaryOperator<Integer> adder = (a, b) -> {
  int sum = a + b;
  return sum;
};
```
### Benefits

- More Readable Code: Reduces boilerplate code.
- Functional Programming: Makes functional programming possible in Java.
- Parallel Processing: Facilitates better use of stream features for parallel processing.

### Usage

#### with Runnable
Traditionally, if you wanted to create a new thread, you might do it with an anonymous inner class:

```java
new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("Traditional way of running a thread.");
    }
}).start();
```
With lambdas, it becomes:

```java
new Thread(() -> System.out.println("Lambda way of running a thread.")).start();
```

#### with Comparator
Old way using anonymous inner class:

```java
List<String> names = Arrays.asList("Mike", "Anna", "Xander", "Zoe");
Collections.sort(names, new Comparator<String>() {
    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }
});
```
Using lambda:

```java
names.sort((o1, o2) -> o1.compareTo(o2));
```
#### with Predicate
With Java's functional interfaces, you can filter data based on conditions:

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
numbers.stream().filter(n -> n % 2 == 0).forEach(System.out::println);  // Prints even numbers
```

#### with Function
Transforming data:

```java
List<String> words = Arrays.asList("hello", "world");
List<Integer> wordLengths = words.stream()
    .map(word -> word.length())
    .collect(Collectors.toList());
System.out.println(wordLengths);  // Prints [5, 5]
```
####  with Consumer
Performing an action on each element of a collection:

```java
List<String> names = Arrays.asList("John", "Jane", "Jack");
names.forEach(name -> System.out.println(name));
```
#### in Stream Operations
Lambdas can be chained in stream operations to form a pipeline of operations:

```java
List<String> animals = Arrays.asList("cat", "dog", "elephant", "bird", "ant");
animals.stream()
    .filter(animal -> animal.length() > 3)
    .map(String::toUpperCase)
    .sorted()
    .forEach(System.out::println);
```
This filters animals with names longer than 3 letters, converts them to uppercase, sorts them, and then prints them.

#### in Event Handling (Swing)
If you're using Swing for GUI, lambdas can make event handlers more concise:

```java
JButton button = new JButton("Click me!");
button.addActionListener(event -> System.out.println("Button clicked!"));
```

## Exercises

### List Filtering
Given a list of integers, filter out all the even numbers.

Task: Use lambdas and the Stream API to accomplish this.

### String Sorting
Given a list of strings, sort the strings based on their length in descending order.

Task: Use lambdas and the Stream API to accomplish this.

### Sum of Squares
Given a list of integers, find the sum of the squares of the numbers.

Task: Use lambdas and the Stream API to calculate the sum.

### Custom Functional Interface
Define a functional interface called StringProcessor that has a method process that takes a string as a parameter and returns a string. Implement this interface using a lambda to reverse the input string.


## Solutions

### List Filtering
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
List<Integer> evenNumbers = numbers.stream()
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList());
System.out.println(evenNumbers);  // Outputs: [2, 4, 6, 8, 10]
```
### String Sorting
```java
List<String> words = Arrays.asList("apple", "banana", "cherry", "date", "fig");
List<String> sortedWords = words.stream()
    .sorted((s1, s2) -> s2.length() - s1.length())
    .collect(Collectors.toList());
System.out.println(sortedWords);  // Outputs: [banana, cherry, apple, date, fig]
```
### Sum of Squares

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
int sumOfSquares = numbers.stream()
    .mapToInt(n -> n * n)
    .sum();
System.out.println(sumOfSquares);  // Outputs: 55
```

### Custom Functional Interface

```java
@FunctionalInterface
interface StringProcessor {
   String process(String str);
}

public class ReverseString {
public static void main(String[] args) {
StringProcessor reverser = str -> new StringBuilder(str).reverse().toString();

        String input = "lambda";
        System.out.println(reverser.process(input));  // Outputs: adbmal
    }
}
```