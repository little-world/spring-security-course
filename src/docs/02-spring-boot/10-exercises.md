# Exercises

### Building a CRUD API using ResponseEntity
Objective: Build a simple CRUD API for managing "Books" using Spring Boot and utilize ResponseEntity to manage HTTP Responses effectively.

#### Set Up Your Spring Boot Application

Utilize Spring Initializr to scaffold a new project and add the Web dependency, or manually add dependencies to your pom.xml file:

```xml
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

#### Create the Book Model

```java
public class Book {
  private Long id;
  private String title;
  private String author;
// Getters and setters...
}
```

#### Implement a Book Controller

Create endpoints for each CRUD operation and ensure they return appropriate ResponseEntity objects.

```java
@RestController
@RequestMapping("/api/books")
public class BookController {

  // Imagine a service & model exists here
  
  @PostMapping
  public ResponseEntity<Book> createBook(@RequestBody Book book) {
    // Implement logic to create a book
    return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Book> getBook(@PathVariable Long id) {
    // Implement logic to retrieve a book by id
    return new ResponseEntity<>(book, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
    // Implement logic to update a book
    return new ResponseEntity<>(updatedBook, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
    // Implement logic to delete a book
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  @GetMapping
  public ResponseEntity<List<Book>> getAllBooks() {
    // Implement logic to get all books
    return new ResponseEntity<>(books, HttpStatus.OK);
  }
}
```

#### Implement Book Service
Create a service that performs the actual business logic of managing books (creating, updating, deleting, retrieving).

#### Implement Error Handling

Enhance the API by handling possible exceptions, like BookNotFoundException, and return appropriate HTTP statuses using ResponseEntity.

#### Testing

Create tests for your API endpoints to ensure they return the expected ResponseEntity objects and status codes.

#### Notes for the Exercise:

- For the purpose of this exercise, you can use a static list to manage books if you don't want to set up a database.
- Explore the usage of @ResponseStatus to specify the status code in conjunction with ResponseEntity.
- Ensure that you handle different possible outcomes (e.g., 404 Not Found for a non-existing book, 400 Bad Request for validation errors) with appropriate HTTP statuses and error messages using ResponseEntity.


- Implement validation (e.g., non-empty book title) and handle validation errors gracefully using ResponseEntity.
- Explore the usage of ResponseEntity with @ExceptionHandler in a @ControllerAdvice to handle exceptions globally and return error details in a consistent format.


### Error Handling

#### Custom Error Messages
Goal: Return custom error messages for client-side validation errors.
Steps:   
Create an endpoint that accepts a payload containing user information (e.g., name, email).    
Implement validation for the payload (using @Valid, @NotNull, etc.).   
Customize error messages and return them in a user-friendly format when validation fails.    

#### Global Exception Handling
Goal: Implement a global exception handler that catches exceptions from all controllers.
Steps:   
Use @ControllerAdvice to implement a global exception handler class.    
Define methods using @ExceptionHandler to handle specific exceptions and return appropriate HTTP status codes and error messages.   
Throw exceptions in your controller methods and ensure they are handled by the global exception handler.

#### Handling Entity Not Found
Goal: Return a 404 Not Found status code and a custom message when an entity is not found.
Steps:   
Create a @RestController that fetches entities from the database.    
Implement an exception that is thrown when an entity is not found.    
Use an exception handler to return a 404 Not Found status and a user-friendly error message when the exception is thrown.    
at when an unauthorized or unauthenticated access attempt is made, your application returns a 403 Forbidden or 401 Unauthorized status (as appropriate) and a clear error message.


### RestTemplate


#### Creating a Service to Fetch Data from an API
Create a service class in which you'll utilize RestTemplate to consume the JSONPlaceholder API, a free online REST API that you can use for testing and prototyping. We'll fetch data about posts from the API.

```java
@Service
public class PostService {

    @Autowired
    private RestTemplate restTemplate;

    public Post[] getPosts() {
        String apiUrl = "https://jsonplaceholder.typicode.com/posts";
        return restTemplate.getForObject(apiUrl, Post[].class);
    }
}
```
Note: Ensure to create a Post class that matches the JSON structure of posts from the API to deserialize the data.

#### Configuring RestTemplate Bean
   Ensure you have configured RestTemplate as a bean so that it can be autowired into your services.

```java
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
```
#### Creating an Endpoint to Display the Fetched Data
   Create a controller that utilizes the PostService to fetch and display posts.

```java
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<Post[]> getPosts() {
        return new ResponseEntity<>(postService.getPosts(), HttpStatus.OK);
    }
}
```
Here, visiting /api/posts should fetch posts from the external API and display them.

#### Handling Exceptions
Implement error handling to manage potential issues like network problems or API changes.

```java
@ExceptionHandler(RestClientException.class)
public ResponseEntity<String> handleRestClientException(RestClientException e) {
return new ResponseEntity<>("Failed to fetch posts: " + e.getMessage(), HttpStatus.BAD_GATEWAY);
}
```
This error handler in your controller will catch exceptions from RestTemplate and return a 502 Bad Gateway response with a relevant error message.

#### Additional Challenges:

- Pagination:    
Utilize query parameters to fetch a specific page or limit of posts from the API and display them.
- Specific Post:    
Create an endpoint that takes an ID as a path variable and displays data for a specific post fetched from the external API.
- Comments:   
Fetch and display comments for a specific post by utilizing additional endpoints provided by the JSONPlaceholder API.
- Custom Exceptions:    
Create a custom exception and exception handler to deal with API-related issues and communicate more specific error details to the client.
- Caching:   
Implement caching to store API responses temporarily and reduce the number of requests to the external API.
- Logging:    
Implement logging to keep track of successful API calls and errors.
