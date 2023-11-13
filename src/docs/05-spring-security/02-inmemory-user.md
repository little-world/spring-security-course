# In Memory User

### HelloController

Change the helloController to 

```java
@RestController
public class HelloController {
  
  @GetMapping("/user")
  String helloUser() {
    return "hello, user";
  }

  @GetMapping("/admin")
  String hellodmin() {
    return "hello, admin";
  }
}
```


### Security Config
```java
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .httpBasic(Customizer.withDefaults())
        .authorizeHttpRequests(request -> request
            .requestMatchers("/user").hasRole("USER")
            .requestMatchers("/admin").hasRole("ADMIN")
            .anyRequest().authenticated()
        );
    return http.build();
  }

}
 ```
```java
@Configuration
public class UserConfiguration {  
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
  
    manager.createUser(User.builder()
        .username("user")
        .password(passwordEncoder.encode("user"))
        .roles("USER")
        .build());

    manager.createUser(User.builder()
        .username("admin")
        .password(passwordEncoder.encode("admin"))
        .roles("ADMIN")
        .build());

    return manager;
  }
}
```
In the above configuration:

- We have defined a PasswordEncoder bean.    
It's crucial to encode passwords before storing or matching them. Using plain text is a significant security risk.
- We have defined an InMemoryUserDetailsManager bean as our UserDetailsService.    
We have added two users: "user" with the role "USER", and "admin" with the role "ADMIN". The passwords are encoded using the PasswordEncoder bean.