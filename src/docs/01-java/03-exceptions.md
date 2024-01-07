# Exceptions

Exception handling is crucial in Java to manage runtime errors so that the normal flow of the application can be maintained. Here is a simplified tutorial that demonstrates basic exception handling in Java:

### Understanding Exceptions
-  Checked Exceptions:    
Are exceptions that are checked at compile-time. You must handle them using try/catch or declare them using throws.
- Unchecked Exceptions:    
Are exceptions that occur at runtime, mainly due to programming errors, like NullPointerException or ArrayIndexOutOfBoundsException.

### Basic Exception Handling

You handle exceptions using try-catch blocks. If an exception occurs in the try block, the corresponding catch block is executed.

```java
try {
  // Code that might throw an exception
  int result = 10 / 0; // This will throw ArithmeticException
} catch(ArithmeticException e) {
  // Handle exception
  System.out.println("Cannot divide by zero");
}
```

### Multiple Catch Blocks
   You can have multiple catch blocks to handle different exceptions separately.


```java
try {
  // Code that might throw multiple exceptions
  String str = null;
  System.out.println(str.length()); // This will throw NullPointerException
} catch(NullPointerException e) {
  System.out.println("String is null");
} catch(Exception e) {
  // This will catch all other exceptions
  System.out.println("Some other error occurred: " + e.getMessage());
}
```
### Finally Block
The finally block always executes, whether an exception is thrown or not. It's useful for cleanup activities, like closing resources.

```java
try {
  // Code that might throw an exception
} catch(Exception e) {
  // Handle exception
} finally {
  // This block is always executed
  System.out.println("Finally block executed");
}
```

### Using Throws Keyword
If you don’t want to handle the exception in the current method, you can use the throws keyword to delegate the responsibility to the calling method.

```java
public void myMethod() throws IOException {
  // Code that might throw IOException
}
```

### Creating Custom Exceptions
You can create your own exception classes by extending the Exception class.


```java
class MyException extends Exception {
  public MyException(String message) {
    super(message);
  }
}
```

### Using Try-with-resources
When dealing with resources like streams, use try-with-resources to ensure that resources are closed automatically.

```java
try(BufferedReader br = new BufferedReader(new FileReader("test.txt"))) {
  // Use br to read from the file
} catch(IOException e) {
  // Handle IO Exception
}
```

### Catching Multiple Exceptions in a Single Catch Block
From Java 7 onwards, you can catch multiple exceptions in a single catch block.

```java
try {
  // Code that might throw multiple exceptions
} catch(IOException | SQLException e) {
  // This block will catch exceptions of type IOException and SQLException
  System.out.println(e.getMessage());
}
```

## Exercises

1. Basic Exception Handling:    
   Create a method called divide that accepts two integers and returns their quotient. If the denominator is zero, catch the ArithmeticException and print "Cannot divide by zero."

2. Custom Exception:    
   Create a custom exception called NegativeNumberException. Write a method that takes an integer and throws this exception if the number is negative.

3. Multiple Exceptions:    
   Write a method that accepts a string and converts it to an integer. This method should catch both NullPointerException (if the input string is null) and NumberFormatException (if the input string cannot be converted to an integer).

4. Throw vs. Throws:    
   Create two methods:
   `validateAge` - This method accepts an age (integer) and throws an ArithmeticException if the age is negative.
   `checkAge` - This method calls validateAge and handles the exception, printing an error message if the age is negative.

5. Finally Block:    
   Write a method that reads a file called "sample.txt" and prints its contents. Use a finally block to ensure that the file is closed properly, even if an exception occurs.

6. Chained Exceptions:    
   Create a method that accepts two integers. If the second integer is zero, throw an ArithmeticException. If the ArithmeticException is thrown, catch it and throw a new IllegalArgumentException with the original exception as its cause.

7. Try-with-resources:    
   Write a method that reads the contents of a file using FileInputStream and prints it. Make sure to use the try-with-resources statement to automatically close the stream.

8. Multiple Resources in Try-with-resources:        
   Enhance the previous exercise by adding a FileOutputStream to write the read contents to a new file. Ensure both streams are managed using a single try-with-resources statement.

9. Rethrowing Exceptions:        
   Create a method that attempts to parse a given string to an integer. If a NumberFormatException is caught, print an error message and then rethrow the exception.

## Solutions

1. Basic Exception Handling:
```java
public int divide(int numerator, int denominator) {
  try {
    return numerator / denominator;
  } catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero.");
    return 0;
  }
}
```
2. Custom Exception:
```java
class NegativeNumberException extends Exception {
  public NegativeNumberException(String message) {
    super(message);
  }
}
public void checkNegative(int number) throws NegativeNumberException {
  if (number < 0) {
    throw new NegativeNumberException("Number is negative.");
  }
}
```

3. Multiple Exceptions:
```java
public int stringToInt(String input) {
  try {
    return Integer.parseInt(input);
  } catch (NullPointerException e) {
    System.out.println("Input string is null.");
    } catch (NumberFormatException e) {
    System.out.println("Cannot convert string to integer.");
  }
  return 0;
}
```

4. Throw vs. Throws:
```java
public void validateAge(int age) throws ArithmeticException {
  if (age < 0) {
    throw new ArithmeticException("Age cannot be negative.");
  }
}
public void checkAge(int age) {
  try {
    validateAge(age);
  } catch (ArithmeticException e) {
    System.out.println(e.getMessage());
  }
}
```
5. Finally Block:
```java
public void readFile() {
  BufferedReader reader = null;
  try {
    reader = new BufferedReader(new FileReader("sample.txt"));
    String line;
    while ((line = reader.readLine()) != null) {
      System.out.println(line);
    }
  } catch (IOException e) {
    System.out.println(e.getMessage());
  } finally {
    try {
      if (reader != null) {
        reader.close();
      }
    } catch (IOException e) {
      System.out.println("Failed to close the file.");
    }
  }
}
```
6. Chained Exceptions:
```java
public void divideWithChain(int a, int b) {
  try {
    if (b == 0) {
      throw new ArithmeticException("Division by zero.");
  }
    System.out.println(a / b);
  } catch (ArithmeticException e) {
    throw new IllegalArgumentException("Invalid division.", e);
  }
}
```
7. Try-with-resources:
```java
public void printFileContents() {
  try (FileInputStream fis = new FileInputStream("sample.txt")) {
    int content;
    while ((content = fis.read()) != -1) {
      System.out.print((char) content);
    }
  } catch (IOException e) {
    System.out.println(e.getMessage());
  }
}
```

8. Multiple Resources in Try-with-resources:
```java
public void copyFileContents() {
try (FileInputStream fis = new FileInputStream("sample.txt");
FileOutputStream fos = new FileOutputStream("copy.txt")) {
int content;
while ((content = fis.read()) != -1) {
fos.write(content);
}
} catch (IOException e) {
System.out.println(e.getMessage());
}
}
```

9. Rethrowing Exceptions:
```java
public int parseString(String s) throws NumberFormatException {
    try {
        return Integer.parseInt(s);
    } catch (NumberFormatException e) {
        System.out.println("Error parsing string to integer.");
    throw e;
    }
}
```

