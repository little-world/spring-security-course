# Defensive programming


Defensive programming in Java involves writing code that can withstand incorrect usage, unexpected inputs, and changes without breaking, thus preventing software bugs and vulnerabilities. It focuses on improving software and source code quality by anticipating errors and handling them gracefully. Here’s a guide to defensive programming in Java.

### Validate Inputs
   Ensure that your methods validate their inputs. Any data received from an external source should be treated as unsafe and must be validated and sanitized.

```java
public void processData(String input) {
  if(input == null || input.trim().isEmpty()) {
    throw new IllegalArgumentException("Input cannot be null or empty");
  } 
// Process the input
}
```
#### Use Final Variables
   Use final variables wherever possible to avoid reassignment and protect the variable from being changed.

```java
public class MyClass {
private final String myString;
    public MyClass(String myString) {
        this.myString = myString;
    }
}
```

#### Immutable Objects
   Design your objects to be immutable, i.e., their state cannot be changed after construction.

```java
public final class ImmutableClass {
private final String value;

    public ImmutableClass(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
```

#### Fail Early
   When an error condition is encountered, it’s usually better to fail early with a clear error message rather than trying to continue in a possibly inconsistent state.

```java
public void doSomething(int value) {
  if(value < 0) 
    throw new IllegalArgumentException("Value must be non-negative");

// Continue with the method
}
```
#### Use Enums 
   Instead of using String constants or integer constants, prefer using enums for a fixed set of values.

```java
public enum Days {
MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
}

```
#### Avoid Null References
   Returning null can lead to NullPointerException. Instead, use Optional or return empty collections or special case objects.

```java
public Optional<String> findString() {
  // Avoid returning null
  return Optional.ofNullable(getString());
}
```

#### Catch Exceptions Appropriately
   Only catch exceptions that you can handle. Let the others propagate to the caller.

```java
try {
  // Some IO operation
} catch(IOException e) {
  // Handle only IOException
}
```

#### Use Assertions
   Use assertions to check the internal consistency of your application, but don’t rely on them for regular runtime checks as they can be disabled at runtime.

```java
assert list != null && list.size() > 0 : "List cannot be null and must contain elements";
```

#### API Contracts
   When overriding methods or implementing interfaces, be aware of the contract that the parent class or interface defines and ensure that your implementation adheres to it.

```java
@Override
public boolean equals(Object obj) {
   if(this == obj) return true;
   if(obj == null || getClass() != obj.getClass()) return false;
   // Continue with other checks
}
```

#### Limit Visibility
Limit the visibility of your classes, methods, and variables as much as possible. Use the least permissive modifier to avoid unintended usage.

```java
private void myPrivateMethod() {
// Only visible within the class
}
```

####  Thread-Safety with Atomic Classes

When modifying shared variables, use atomic classes or synchronization to avoid data corruption due to concurrent modifications.
```java
import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
private final AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }
}
```

### Managing External Resource Dependencies

Handle scenarios where external resources like databases or APIs may be unavailable, using retries with exponential backoff, circuit breakers, or timeouts.

Here, we will use a hypothetical CircuitBreaker class, inspired by the resilience4j library.

```java
import java.time.Duration;

public class ExternalServiceClient {

    private final CircuitBreaker circuitBreaker;

    public ExternalServiceClient() {
        // Hypothetical CircuitBreaker with max 3 retries, 1-second initial backoff, and 10-second timeout.
        this.circuitBreaker = new CircuitBreaker(3, Duration.ofSeconds(1), Duration.ofSeconds(10));
    }

    public String callExternalService() throws ExternalServiceException {
        try {
            return circuitBreaker.execute(() -> {
                // Invoke external service
                return externalService.call();
            });
        } catch (Exception e) {
            throw new ExternalServiceException("Failed to call external service", e);
        }
    }
}
```

#### ReadOnly or Unmodifiable Collections

Return read-only views of collections to protect internal state.

```java
private final List<String> items = new ArrayList<>();

public List<String> getItems() {
   return Collections.unmodifiableList(items);
}
```

## Exercises

### Refactor to Immutable
Refactor Mutable Class to Immutable: Take an existing class with mutable state and refactor it into an immutable class. This exercise helps in understanding the practical challenges and solutions when working with legacy code.

``` java
public class Person {
private String name;
private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```


### Builder pattern
Implement the immutable class Person using the Builder pattern. This is useful for classes with many fields, some of which may be optional. The Builder pattern can provide more readable and maintainable code.


### Refactor to record
Immutable Class with Java Records (Java 14 and above): If you are using Java 14 or later, create an immutable class using Java Records, which is a feature specifically designed to simplify the creation of immutable data carriers.


#### Null Safety
You have a method that takes a String and performs operations, such as trimming, converting to upper case, etc.

Implement null safety to ensure that if a null value is passed to the method, it does not throw a NullPointerException and instead handles it gracefully.   
Utilize Optional to manage potential null values in a more functional way.



## Solutions

### Refactor to Immutable Class

```java

public final class Person {
private final String name;
private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
```

In the immutable version (ImmutablePerson):

- The class is declared final to prevent subclassing.
- All fields are final to ensure they are immutable.
- There are no setter methods.
- The constructor sets all properties.

Once a ImmutablePerson object is created, its state cannot be altered, which is the essence of immutability.


### Builder pattern

To incorporate the Builder pattern with the Person class, we create a static nested Builder class inside Person. This pattern is useful when dealing with objects that have several fields, some of which might be optional. It improves code readability and maintainability.

```java
public final class Person {
  private final String name;
  private final int age;
  private final String address; // Optional field

  private Person(String name, int age, String address) {
    this.name = name;
    this.age = age;
    this.address = address;
  }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    // Static Builder class
    public static class Builder {
        private String name;
        private int age;
        private String address;
        

      public Builder name(String name) {
        this.name = name;
        return this;
      }

      public Builder age(int age) {
        this.age = age;
        return this;
      }
      
      public Builder address(String address) {
        this.address = address;
        return this;
      }

      public Person build() {
        return new Person(name, age, address);
      }
    }
}
```
How to Use the Builder:
```java
public class Main {
  public static void main(String[] args) {
    Person person = new Person.Builder()
      .name("John Doe")
      .age(30)
      .address("123 Main St")
      .build();

      System.out.println("Name: " + person.getName());
      System.out.println("Age: " + person.getAge());
      System.out.println("Address: " + person.getAddress());
    }
}
```
In this implementation:

- Person has a private constructor and a nested static Builder class.
- The Builder class has methods for setting optional fields and a build() method that creates the Person instance.
- Fields are set using fluent Builder methods.

### Input Validation


### Null Safety

```java
public class StringUtils {
  public String toUpperCase(String input) {
    return Optional.ofNullable(input).map(String::toUpperCase).orElse(null);
  }
}
```
