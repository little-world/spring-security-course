# Spring Test

Set Up Your Spring Boot Application
   Use Spring Initializr to create a new project with Web and JPA dependencies, or create manually by adding dependencies to your pom.xml file:

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```
### Create the To-Do Model
   Create a simple model for the Todo entity.

```java
@Entity
  public class Todo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private boolean completed;
  // Getters and setters...
}
```
### Build the Repository
Create a JPA repository to handle data access:

```java
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
```

### Implement a Service
Create a service to manage business logic related to your Todo entity:

```java
@Service
public class TodoService {
  @Autowired
  private TodoRepository repository;

  public List<Todo> findAll() {
    return repository.findAll();
  }
  // Additional CRUD methods...
}
```
### Create Controllers
Develop a controller to manage HTTP requests related to Todo entities:


```java
@RestController
@RequestMapping("/api/todos")
public class TodoController {
@Autowired
private TodoService service;

  @GetMapping
  public List<Todo> getAllTodos() {
    return service.findAll();
  }
  // Additional endpoint methods...
}
```

### Write Tests Using Spring Test

Create tests using Spring Test to ensure the functionality of your application:

```java
@SpringBootTest
public class TodoServiceTest {

  @Autowired
  private TodoService service;

  @MockBean
  private TodoRepository repository;

  @Test
  public void whenFindAll_thenReturnAllTodos() {
    Todo todo = new Todo();
    todo.setTitle("Test Todo");
    List<Todo> allTodos = Arrays.asList(todo);
    
    given(repository.findAll()).willReturn(allTodos);
    
    List<Todo> foundTodos = service.findAll();
      
    assertThat(foundTodos).hasSize(1);
    assertThat(foundTodos.get(0).getTitle()).isEqualTo(todo.getTitle());
  }
  // Additional test methods...
}
```
Run Your Application and Tests
Run your Spring Boot application and ensure that all tests pass. If you're using an IDE like IntelliJ IDEA or Eclipse, you can generally run the app or the tests with a single click. Alternatively, you can use Maven:

To run the application:
```text
mvn spring-boot:run
```
To run the tests: 
```text
mvn test
```
