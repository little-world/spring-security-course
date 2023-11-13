#  Getting Started

Create a new project on 
start.spring.io
Web. Security

This should add two dependencies to the pom.xml
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

### HelloController
Add a controller 

```java
@RestController
public class HelloController {
  
  @GetMapping("/hello")
  String sayHello() {
    return "hello";
  }
}
```

### Run 
Run the appplication 
```text
mvn spring-boot:run
```
A password is generated:
```text
.UserDetailsServiceAutoConfiguration : 

Using generated security password: 60be5ee9-110d-4c24-acf0-ebf42f4e05f3

This generated password is for development use only. Your security configuration must be updated before running your application in production.
```

#### In Postman 
GET localhost:8080/hello
Authentication 
  user: user
  password: 60be5ee9-110d-4c24-acf0-ebf42f4e05f3

#### in curl

```text
curl --user user:60be5ee9-110d-4c24-acf0-ebf42f4e05f3 http://localhost:8080/hello
```


### application.properties

Add a username and password to the application.properties

```text
spring.security.user.name=admin
spring.security.user.password=admin
```

Now a password is not generated anymore.

### Principal

Add a Principal parameter to the HelloController

```java
import java.security.Principal;

@RestController
public class HelloController {

  @GetMapping("/hello")
  String sayHello(Principal principal) {
    return "hello, " + principal.getName();
  }
}
```

#### Rerun
```text
curl --user admin:admin http://localhost:8080/hello
```



