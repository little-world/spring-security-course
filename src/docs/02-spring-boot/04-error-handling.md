# Error Handling

### ResponseEntity

```java
ResponseEntity<?> save(@RequestBody Todo todo) {
  if (todo.getTask() != null )
    return todoService.save(todo), HttpStatus.OK);
  else
    return new ResponseEntity<String>("can not save", HttpStatus.BAD_REQUEST)
}
```

We have to wrap the response in en ResponseEntity

- the happy path return a Todo with HttpStatus.OK
- the error path return a String with HttpStatus.BadRequest
- The problem is `ResponseEntity<?>` we have to generalise the Todo and String to question mark.    
We lost the Java types

### @ExceptionHandler

```java
public Todo updateTodo(@RequestBody Todo todo) throws Exception {
  if (todo.getTask() != null )  
    return todoService.save(todo);
  else
    throw new Exception("nothing to update");
}

```
In this case we can return a Todo or throw an Exception
- The return a healthy Todo   
- The Exception is catch by an @ExceptionHandler method

```java
@ResponseStatus(HttpStatus.BAD_REQUEST)
@ExceptionHandler(Exception.class)
public String exceptionHandler(Exception ex) {
  return "exception: " + ex.getMessage();
}
```


### ControllerAdvice
We could make an ExceptionHandler available for all Controller
by putting it in an @ControllerAdvice

```java
@RestControllerAdvice
public class TodoExceptionHandler {

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(Exception.class)
  public String exceptionHandler(Exception ex) {
    return "exception: " + ex.getMessage();
  }
}
```

# Validation API
With the ValidationAPI we can annotate our Todo class with 


```java
@Entity
public class Todo {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  int id;

  @NotBlank
  String task;
}
```

We annotate the save method with @Valid 
```java
@PostMapping("/todo")
Todo save(@Valid @RequestBody Todo todo)  {
   return todoService.save(todo);
}
```
On an error a `MethodArgumentNotValidException` is thrown
To read the useful error message we dig for the FieldErrors in the exception


```java
@ResponseStatus(HttpStatus.BAD_REQUEST)
@ExceptionHandler(MethodArgumentNotValidException.class)
public List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException ex) {
  BindingResult result = ex.getBindingResult();
  List<FieldError> fieldErrors = result.getFieldErrors();
  return fieldErrors.stream().map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage())).toList();
}
```
We will create our own ErrorMessage class. That is used as a json template.

```java
public class FieldErrorMessage {
  private String field;
  private String message;


  public FieldErrorMessage(String field, String message) {
    this.field = field;
    this.message = message;
  }
  // getters and setters
}
```
