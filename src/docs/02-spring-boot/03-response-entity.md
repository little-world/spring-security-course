# ResponseEntity

ResponseEntity is a class in Spring Framework that represents the whole HTTP response: status code, headers, and body. Using it, you can fully configure the HTTP response. Here’s a simple tutorial to illustrate how to use ResponseEntity in a Spring Boot application.

### Create a Spring Boot Project
Create a new Spring Boot project using Spring Initializer or your favorite IDE. Add Spring Web as a dependency.

### Create a Simple Entity
Create a simple Book entity for illustration purposes.

```java
public class Book {
private String title;
private String author;

    // Constructors, getters, and setters
}
```

### Create a Controller
Create a BookController class with endpoints that return ResponseEntity:

```java
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @GetMapping("/book")
    public ResponseEntity<Book> getBook() {
        Book book = new Book("The Hobbit", "J.R.R. Tolkien");
        
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
    
    @GetMapping("/no-content")
    public ResponseEntity<Void> noContent() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/custom-header")
    public ResponseEntity<Book> customHeader() {
        Book book = new Book("The Hobbit", "J.R.R. Tolkien");
        
        return ResponseEntity.ok()
                             .header("Custom-Header", "custom-value")
                             .body(book);
    }
}
```

- `getBook()`:
Creates a Book object and returns it with an HTTP 200 (OK) status code.
This is a standard way to return objects and status codes.
- `noContent()`:
Returns an HTTP 204 (No Content) status code with no body.
Useful when the operation is successful but there’s no data to return.
- `customHeader()`:
Returns a Book object, an HTTP 200 (OK) status code, and a custom header.
Demonstrates how to customize the HTTP response headers using ResponseEntity.

### Run the Application
Run your Spring Boot application and try accessing the endpoints using a tool like curl or Postman.

Example of Using curl:
```text
curl -i http://localhost:8080/book
```

### Analyze the Response
When you make a request to these endpoints, analyze the HTTP response. You will see the status codes, headers, and body as per the configurations in the Controller methods.


### Bonus Tips:
####Builder Pattern:
ResponseEntity has a builder API, allowing for fluent API chaining, e.g., 

> `ResponseEntity.status(…).header(…).body(…).build()`.

#### Shorthand Methods:
For standard responses, use shorthand methods like 

> `ResponseEntity.ok(body)` for HTTP 200 
> `ResponseEntity.notFound().build()` for HTTP 404.