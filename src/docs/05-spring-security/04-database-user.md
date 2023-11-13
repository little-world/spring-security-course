# User in Database 


### 

Add H2 and Spring Data to the pom.xml
```xml
 <dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
  <scope>runtime</scope>
</dependency>
```

### User Entity and Repository
Create a `User` class, Rename the table to `users` for H2.
Give it the fields username, password and roles with getters and setters and constructor

```java
@Entity(name = "users")
public class User {
  @Id
  int id;
  String username;
  String password;
  String roles;

  public User(String username, String password, String roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

  public User() {
  }
  
  // getters and setters
}
```
Create a `UserRepository` with a `findByUsername()` method

```java
public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByUsername(String name);
}
```

### UserDetails interfaces

In Spring Security we have to implement two interfaces: 

- UserDetails 
- UserDetailsService

#### UserDetails
```java
public class UserDetailsImpl implements UserDetails {
  String username;
  String password;
  Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(User user) {
    this.username = user.getUsername();
    this.password = user.getPassword();

    this.authorities = Arrays.stream(user.roles.split(","))
        .map(SimpleGrantedAuthority::new)
        .toList();
  }
  
  // implements the UserDetails interface
  // the getters are added
  // and return true on all the booleans
}
```
The constructor is copy constructor from User to UserDetails.
The user roles are split and in a stream() converted to a list of GrantedAuthority

#### UserDetailsService

```java
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UserRepository userRepository;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return new UserDetailsImpl(
        userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("not found " + username))
    );
  }
}
```

The loadUserByUsername is delegated to findByUsername on the UserRepository.
A UsernameNotFoundException is thrown when a user is not available.

In the Configuration the in memory userDetailsService is removed. 
And because the UserDetailsServiceImpl is annotated with @Service this in now available via dependency injection



### Add users
We add the same users `user` and `admin` to the database

```java
// in @SpringBootApplication
@Bean
CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder encoder) {
  return args -> {
    userRepository.save(new User("user", encoder.encode("user"), "ROLE_USER"));
    userRepository.save(new User("admin", encoder.encode("admin"), "ROLE_ADMIN"));
  };
}
```
Because the roles are converted to Authorities we have to put `ROLE_` before every role name.    
(e.g. USER -> ROLE_USER)




