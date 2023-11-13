# Method Security
Allows you to define access control rules to your methods, typically at the service layer or controller layer, using a SpEL (Spring Expression Language) based syntax.

### Enable  Method Security:
In your Spring Security configuration class (a class annotated with @EnableWebSecurity or @Configuration and extends WebSecurityConfigurerAdapter), you need to add the @EnableGlobalMethodSecurity annotation to enable method-level security:
```java
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled)
public class SecurityConfigation {
// your configuration here
}
```
By setting `prePostEnabled`, you're enabling support for `@PreAuthorize` and `@PostAuthorize` annotations.


### Use the @PreAuthorize Annotation:
You can now use the @PreAuthorize annotation on methods to define access control rules:
```java
// inside the HelloController
@GetMapping("/user")
@PreAuthorize("hasRole('USER')")
String helloUser() {
  return "hello, user";
}
```   
How It Works:

- When a method annotated with @PreAuthorize is invoked, Spring Security will evaluate the SpEL expression defined in the annotation.    
- If the expression evaluates to true, the method is invoked.   
- If the expression evaluates to false, an `AccessDeniedException` is thrown, indicating the current user doesn't have the required permission to access the method.


### Commonly Used Expressions:

- `hasRole('ROLE_XXX')`: Checks if the current user has the specified role.
- `hasAuthority('XXX')`: Checks if the current user has the specified authority.
- `isAuthenticated()`: Returns true if the user is not anonymous.
- `isAnonymous()`: Returns true if the user is anonymous.
- `isRememberMe()`: Returns true if the user was authenticated using a remember-me token.

Using @PreAuthorize can help centralize and declutter your authorization logic, especially when dealing with complex requirements. However, ensure that you're combining it with other layers of security (like URL-based security) to create a robust security posture.