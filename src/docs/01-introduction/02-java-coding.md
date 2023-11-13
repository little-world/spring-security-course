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


#### Input Validation

You have a method that takes a username and password to create a user account.

Implement input validation to ensure that the username and password comply with specified rules, e.g., minimum lengths, containing special characters, etc.
Implement custom exception handling to throw and catch specific exceptions related to validation.

#### Null Safety
You have a method that takes a String and performs operations, such as trimming, converting to upper case, etc.

Implement null safety to ensure that if a null value is passed to the method, it does not throw a NullPointerException and instead handles it gracefully.   
Utilize Optional to manage potential null values in a more functional way.

#### Immutable Data Structures

You have a Person class that has properties like name, age, etc.


Make the Person class immutable to ensure that once an instance is created, it cannot be modified.    
Implement a method that returns a new instance of Person with modified properties, ensuring the original instance remains unchanged.


#### Error Handling in I/O Operations
You have a method that reads a file and processes the data.
Implement error handling to manage potential issues, such as the file not being found, I/O errors, or data format issues.    
Implement a retry mechanism to attempt reading the file multiple times before finally throwing an error.

#### Concurrent Modification
You have a method that modifies a List while iterating over it.

Implement logic to safely manage concurrent modifications to the List to avoid ConcurrentModificationException.     
Explore using CopyOnWriteArrayList and understand its pros and cons.


## Solutions

#### Input Validation

```java
public class UserService {
  public void createUser(String username, String password) {
    if (username == null || username.length() < 5 || !username.matches("^[a-zA-Z0-9_]+$")) {
      throw new IllegalArgumentException("Invalid username");
    }
    if (password == null || password.length() < 8) {
      throw new IllegalArgumentException("Invalid password");
    }
// Create user...
  }
}
```

#### Null Safety

```java
public class StringUtils {
  public String toUpperCase(String input) {
    return Optional.ofNullable(input).map(String::toUpperCase).orElse(null);
  }
}
```

#### Immutable Data Structures

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

  public Person withName(String newName) {
    return new Person(newName, this.age);
  }

  public Person withAge(int newAge) {
    return new Person(this.name, newAge);
  }
}
```

#### Error Handling in I/O Operations

```java
import java.io.IOException;
import java.nio.file.*;

public class FileReader {
  public String readFile(String path) {
    try {
      return new String(Files.readAllBytes(Paths.get(path)));
    } catch (IOException e) {
      throw new RuntimeException("Error reading file: " + path, e);
    }
  }
}
```

#### Concurrent Modification

```java
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListModifier {
  public void modifyList(List<String> list) {
    List<String> safeList = new CopyOnWriteArrayList<>(list);
    for (Iterator<String> iterator = safeList.iterator(); iterator.hasNext(); ) {
      String item = iterator.next();
      // perform modifications, such as remove or add elements to safeList
    }
    list.clear();
    list.addAll(safeList);
  }
}
```
Notes:

- Input Validation: Ensure valid inputs are passed, or throw relevant exceptions.
- Null Safety: Utilize Optional to prevent NullPointerException and handle null gracefully.
- Immutable Data Structures: Immutable objects can't be altered once they're created. They are thread-safe and have no risk of being changed unexpectedly.
- Error Handling in I/O Operations: Use try/catch to handle I/O errors and manage failure scenarios gracefully.
- Concurrent Modification: Using CopyOnWriteArrayList can be helpful to avoid ConcurrentModificationException when modifying a list during iteration, but beware of its performance implications on write-heavy use cases.
