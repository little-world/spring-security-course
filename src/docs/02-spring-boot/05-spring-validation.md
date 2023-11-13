# Validation API

In Spring Boot, you can easily use javax.validation with the Hibernate Validator implementation for validating request payloads, method parameters, and other application components. Here’s a simple tutorial to illustrate how to use javax.validation in a Spring Boot application.

### Create a Spring Boot Project
Create a new Spring Boot project using Spring Initializer or your favorite IDE. Add Spring Web and Spring Boot Starter Validation as dependencies.

For Maven, the dependencies in your pom.xml should look like this:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```
### Create a Bean with Validation Constraints
Create a User class with validation annotations:

```java
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {

    @NotNull(message = "Username cannot be null")
    @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters")
    private String username;
    
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    
    // Constructors, getters, and setters
}
```

### Create a Controller
Create a UserController class with an endpoint that consumes a User object:

```java
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        // If any validation constraints are violated, a MethodArgumentNotValidException is thrown,
        // and a 400 Bad Request response is returned automatically by Spring Boot.
        
        return ResponseEntity.ok("User is valid!");
    }
}
```

### Handle Validation Errors
Spring Boot provides default error messages for invalid requests, but you can customize them or handle validation errors differently by defining an ExceptionHandler method in your controller or a ControllerAdvice class.

Here’s an example of a simple ControllerAdvice to handle validation errors:

```java
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((org.springframework.validation.FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
```
### Run the Application
Run your Spring Boot application and try creating a user with invalid data by making a POST request to http://localhost:8080/users.

For instance, you can use curl to make a request with an invalid user:

```text
curl -X POST -H "Content-Type: application/json" -d '{"username":"John", "password":"123"}' http://localhost:8080/users
```
You should receive a 400 Bad Request response with the validation error messages.

