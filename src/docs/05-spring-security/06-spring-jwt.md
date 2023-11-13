# Spring JWT

Integrating JWT (JSON Web Tokens) in a Spring Boot application can be achieved using the java-jwt library provided by jsonwebtoken.io. Here's a step-by-step tutorial to set up JWT-based authentication for a Spring Boot REST API.


### Dependencies 

Starting with a basic spring web, security project.
First, add the `jsonwebtoken` and `lombok` dependency to your Maven pom.xml:

```xml
<dependency>
   <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-api</artifactId>
  <version>0.12.3</version>
</dependency>
<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-impl</artifactId>
  <version>0.12.3</version>
  <scope>runtime</scope>
</dependency>
<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-jackson</artifactId>
  <version>0.12.3</version>
  <scope>runtime</scope>
</dependency>

<dependency>
  <groupId>org.projectlombok</groupId>
  <artifactId>lombok-maven-plugin</artifactId>
  <version>1.18.20.0</version>
  <scope>provided</scope>
</dependency>
```

We are using the latest version of this library. On the web there are a lot of examples with older versions
But a lot of the function names are deprecated (with a good reason)

### JWT Utility Class
Create a utility class for generating and validating JWT tokens.
We add a getUsernameFromToken method to extract the the username from the token subject.

```java
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
  private final SecretKey secretKey = Jwts.SIG.HS256.key().build();

  public String generateToken(String username) {
    return Jwts.builder()
        .signWith(secretKey)
        .subject(username)
        .compact();
  }

  public boolean validateToken(String authToken) {
    JwtParser parser = Jwts.parser()
        .verifyWith(secretKey)
        .build();
    parser.parseSignedClaims(authToken);
    return true;
  }
  
  public String getUserNameFromToken(String token) {
    JwtParser parser = Jwts.parser()
        .verifyWith(secretKey)
        .build();

    Claims claims = parser.parseSignedClaims(token).getPayload();
    return claims.getSubject();
  }
}
```

### Security Configuration

Add a filter to the securityFilterChain
```java
httpSecurity.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

```
Add a filter @Bean 

```java
@Bean
public TokenAuthenticationFilter tokenAuthenticationFilter() {
    return new TokenAuthenticationFilter();
}
```

Implement the filter 

```java
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var requestHeader = request.getHeader("Authorization");
        try {
            if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
                String token = requestHeader.substring(7);

                if (jwtUtil.validateToken(token)) {
                    String username = jwtUtil.getUserNameFromToken(token);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    // holds the logged in user
                    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                }
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }
}
```

A filter sits before the Controller (and Servlets) in Spring Web. 
- From the request the token is fetch from the `Authorization` header
- the token is validated and the username is extracted
- with the username we check if the username is present in the database
- With the UserDetails the authentication is set 
- in the last statement the filter is forwarded

### LoginController
In the controller we do the `registration` and `login` of a user

- The `register` method will add a new user to the database.    
This delegated to the RegistrationService. We will use UserDTO as json wrappers.
- The `login` method will check the user a return a JWTResponse with a generated token.

```java
@AllArgsConstructor
public class LoginController {
    AuthenticationManager authenticationManager;
    UserDetailsServiceImpl userDetailsService;
    RegisterService registerService;
    JwtUtil tokenProvider;

    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
      User newUser = registerService.registerNewUser(userDTO);
      System.out.println("user id " + newUser.getId());
      return new UserDTO(newUser.getUsername(), newUser.getPassword());
    }
    
    @PostMapping("/login")
    public JWTResponse authenticateUser(@RequestBody UserDTO userDTO) throws Exception {
        Authentication auth = authenticate(userDTO.getUsername(), userDTO.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUsername());
        String jwt = tokenProvider.generateToken(userDetails.getUsername());
        return new JWTResponse(jwt, new UserDTO(userDetails.getUsername(), userDetails.getPassword()));
    }
    
    private Authentication authenticate(String username, String password) throws Exception {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        return authentication;
    }
}
```


```java
@Service
@AllArgsConstructor
public class RegisterService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public User registerNewUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles("ROLE_USER");

        return userRepository.save(user);
    }
}
```

### User, UserDTO, Message and JWTResponse

All these classes only contain data simplified with lombok

```java
@Setter
@ToString
@Entity(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String username;
    String password;
    String roles;
}
```

```java
@Getter
@AllArgsConstructor
public class UserDTO {
    String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
}
```

```java
@Getter
@AllArgsConstructor
public class Message {
    String text;
}
```

```java
@Getter
@AllArgsConstructor
public class JWTResponse {
    String token;
    UserDTO user;
}
```


### github

[github jwt-spring](https://github.com/little-world/jwt-spring)






