# Testing Tutorial

### Getting

Load the basic todo app https://github.com/little-world/tutorial-todo-backend

The test are written in de directory: src/test/java

Already one test is generated: `TodoApplicationTests.java`

### Smoke and Sanity Test
Add the TodoController to the test
And test if its is not null.

```java
@SpringBootTest
class TodoApplicationTests {
  @Autowired
  TodoController todoController;
  @Test
  void contextLoads() {
    Assertions.assertNotNull(todoController); 
  }
}
```
             
### ControllerTest

Add a new test toTodoApplicationTests create a Todo

```java
@Test
void createTodo() {
  Todo todo=new Todo();
  todo.setTask("watch the video");
  Todo newTodo=todoController.save(todo);
  Assertions.assertNotNull(newTodo);
}
```

### RestTemplate
Create new class 
A RestTemplate is a blocking webclient. Starting with the POST
                
```java
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TodoRestTemplateTests {
  @LocalServerPort
  int randomServerPort;

  @Test
  @Order(1)
  public void testCreate() throws URISyntaxException {
    RestTemplate restTemplate = new RestTemplate();

    final String baseUrl = "http://localhost:" + randomServerPort + "/todo/";
    URI uri = new URI(baseUrl);

    Todo todo = new Todo();
    todo.setTask("watch the video");

    ResponseEntity<Todo> result = restTemplate.postForEntity(uri, todo, Todo.class);

    //Verify request succeed
    Assertions.assertEquals(200, result.getStatusCodeValue());
    Assertions.assertEquals(result.getBody().getTask(), todo.getTask());

  }
}
```
Next is the GET with a findAll()

```java
@Test
@Order(2)
public void testFindAll() throws URISyntaxException {
  RestTemplate restTemplate = new RestTemplate();

  final String baseUrl = "http://localhost:" + randomServerPort + "/todo/";
  URI uri = new URI(baseUrl);


  ResponseEntity<Todo[]> result = restTemplate.getForEntity(uri, Todo[].class);

  //Verify request succeed
  Assertions.assertEquals(200, result.getStatusCodeValue());
  Assertions.assertEquals(result.getBody()[0].getTask(), "watch the video" );

}                                                    
```

### WebTestClient

A WebTestClient is the test version of the reactive Webclient.   
Starting with the POST

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TodoWebTestClientTests {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  @Order(1)
  public void testCreate() {
    Todo todo = new Todo();
    todo.setTask("watch the video");

    webTestClient.post().uri("/todo")
       .contentType(MediaType.APPLICATION_JSON)
       .accept(MediaType.APPLICATION_JSON)
       .body(Mono.just(todo), Todo.class)
       .exchange()
       .expectStatus().isOk()
       .expectHeader().contentType(MediaType.APPLICATION_JSON)
       .expectBody()
       .jsonPath("$.task").isNotEmpty()
       .jsonPath("$.task").isEqualTo(todo.getTask());
  }
}
```

Next is the GET with a findAll()

```java
@Test
@Order(2)
public void testFindAll() {

  webTestClient.get().uri("/todo")
     .accept(MediaType.APPLICATION_JSON)
     .exchange()
     .expectStatus().isOk()
     .expectHeader().contentType(MediaType.APPLICATION_JSON)
     .expectBodyList(Todo.class)

     .hasSize(1);
}
```

### MockMvc
We will use mocking to isolate TodoController from the TodoService

```java
@WebMvcTest(TodoController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TodoMockMvcTests {

  @Autowired
  MockMvc mvc;

  @MockBean
  TodoService todoRepository;

  @Test
  @Order(1)
  public void testCreate() throws Exception {

    Todo todo = new Todo();
    todo.setId(0);
    todo.setTask("watch the video");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(todo);

    System.out.println(json);

    when(todoRepository.save(any())).thenReturn(todo);

    mvc.perform(post("/todo")
       .contentType(MediaType.APPLICATION_JSON)
       .content(json)
       .accept(MediaType.APPLICATION_JSON))
       .andExpect(status().isOk())


       .andExpect(jsonPath("$.task").value(todo.getTask()));
  }
}
```

```java
@Test
@Order(2)
public void testFindAll() throws Exception {
  List<Todo> mockTodos = new ArrayList<Todo>();
  mockTodos.add(new Todo(0, "task0"));
  mockTodos.add(new Todo(1, "task1"));

  when(todoRepository.findAll()).thenReturn(mockTodos);

  mvc.perform(get("/todo"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$", hasSize(2)))
    .andExpect(jsonPath("$[0].id").value(0)).andExpect(jsonPath("$[0].task").value("task0"))
    .andExpect(jsonPath("$[1].id").value(1)).andExpect(jsonPath("$[1].task").value("task1"));
}
```

## Exercises

Follow the steps in the tutorial

