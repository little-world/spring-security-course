# HTTP Status

HTTP status codes are issued by a server in response to a client's request made to the server. They represent the outcome of the server's attempt to process the request. Here’s a brief tutorial on understanding HTTP status codes and simulating them using a simple Spring Boot application.

HTTP Status Code Categories:
- `1xx (Informational)`: The request was received, and the server is continuing to process it.
- `2xx (Successful)`: The request was successfully received, understood, and accepted.
- `3xx (Redirection)`: Further action needs to be taken in order to complete the request.
- `4xx (Client Error)`: The request contains bad syntax or cannot be fulfilled by the server.
- `5xx (Server Error)`: The server failed to fulfill a valid request.

### Common HTTP Status Codes:

- `200 OK`: The request was successful.
- `201 Created`: The request was successful, and a resource was created.
- `204 No Content`: The server successfully processed the request, but there is no content to send in the response.
- `400 Bad Request`: The request could not be understood by the server due to malformed syntax.
- `401 Unauthorized`: The request requires user authentication.
- `403 Forbidden`: The server understood the request but refuses to fulfill it.
- `404 Not Found`: The server has not found anything matching the Request-URI.
- `500 Internal Server Error`: The server encountered an unexpected condition which prevented it from fulfilling the request.

### Simulate HTTP Status Codes:
Setup Spring Boot Project:
Create a new Spring Boot project using Spring Initializer or your favorite IDE. Choose Spring Web as a dependency.

### Create a Controller:
Create a StatusCodeController class to simulate different HTTP status codes:

```java
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusCodeController {
    @GetMapping
    public ResponseEntity<String> getStatus(@RequestParam("code") int code) {
        HttpStatus status = HttpStatus.resolve(code);
        if (status == null) return new ResponseEntity<>("Invalid HTTP Status Code", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(status.getReasonPhrase(), status);
    }
}
```
In this controller, you can pass any HTTP status code as a query parameter, and the server will respond with the respective status code.
Run the Application:
Start the Spring Boot application, and test different status codes using curl or any API testing tool like Postman.

```text
curl -i http://localhost:8080/status?code=200
```

