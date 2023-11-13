# Spring Headers

Spring Security provides a range of options for managing HTTP headers to enhance the security of your web applications. Here are a few headers and how you might configure or use them:

### Content Security Policy (CSP)
   CSP helps prevent various types of attacks like cross-site scripting (XSS) by controlling the resources which user agents are allowed to load.

```java
http.headers().contentSecurityPolicy("script-src 'self'");
```

### HTTP Strict Transport Security (HSTS)
HSTS is a web security policy mechanism that helps to protect websites against man-in-the-middle attacks such as protocol downgrade attacks and cookie hijacking.

```java
http.headers().httpStrictTransportSecurity().includeSubDomains(true).maxAgeInSeconds(31536000);
```
### X-Content-Type-Options
The X-Content-Type-Options response HTTP header is a marker used by the server to indicate that the MIME types advertised in the Content-Type headers should not be changed and be followed.

```java
http.headers().contentTypeOptions();
```
### Referrer Policy
Referrer Policy is a new header that allows a site to control how much information the browser includes with navigations away from a document and should be sent to the site when the browser fetches a sub-resource.

```java
http.headers().referrerPolicy(ReferrerPolicy.NO_REFERRER);
```
   
###   Frame Options
The X-Frame-Options HTTP response header can be used to indicate whether a browser should be allowed to render a page in a <frame>, <iframe>, <embed>, or <object>.

```java
http.headers().frameOptions().deny();
```
### Feature Policy
  The Feature-Policy header provides a mechanism to allow and deny the use of browser features in its own frame, and in iframes that it embeds.

```java
http.headers().featurePolicy("geolocation 'self'");
```
Example Configuration Using Java Config in Spring Security
Here's how you might configure a subset of these headers using Java Configuration in Spring Security:

```java
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // other http configuration here
            .headers()
                .contentSecurityPolicy("script-src 'self'")
                .and()
                .referrerPolicy(ReferrerPolicy.NO_REFERRER)
                .and()
                .featurePolicy("geolocation 'self'")
                .and()
                .frameOptions().deny()
                .and()
                .httpStrictTransportSecurity()
                    .includeSubDomains(true)
                    .maxAgeInSeconds(31536000);
    }
}
```

### HTTP Headers
When you configure Spring Security headers as in the example in the previous message, the resulting HTTP headers for a response would look something like the following. Note that the actual headers may vary based on your precise configuration and the request being handled.

```text
HTTP/1.1 200 OK
Content-Security-Policy: script-src 'self'
Referrer-Policy: no-referrer
Feature-Policy: geolocation 'self'
X-Frame-Options: DENY
Strict-Transport-Security: max-age=31536000; includeSubDomains
X-Content-Type-Options: nosniff
```


## Exercises

### Configure Content Security Policy Header
Create a Spring Boot project and configure the Content Security Policy header to allow scripts only from the same origin and styles from a specific external domain (e.g., https://maxcdn.bootstrapcdn.com).

### Enable and Configure HSTS
In a Spring Boot application, enable HSTS (HTTP Strict Transport Security) and ensure that it applies to subdomains, with a max age of 1 year (31,536,000 seconds).

### Disable Embedding of Your Application
Create a Spring Boot application, and configure it in such a way that it can't be embedded in an iframe, regardless of where the request comes from.

### Referrer Policy
Configure a Spring Boot application to restrict the values sent in the Referer header when navigating away from your application, by applying a strict no-referrer policy.

### Content Type Options
Ensure that the browser will not interpret files as a different MIME type in your Spring Boot application by adding the appropriate HTTP header.

### Feature Policy
Allow only the application’s origin to access and use the camera and microphone by configuring the Feature-Policy header.

### Custom Headers
Implement custom HTTP headers in your Spring Boot application for specific endpoints, such as X-Custom-Header: CustomValue.

### Conditionally Apply Headers
Configure your Spring Boot application to apply certain security headers only when the application is running in a production environment, not in development or test environments.

### Handle CORS
Implement and configure Cross-Origin Resource Sharing (CORS) in your Spring Boot application to allow requests from a specific frontend domain.

### Cache Control
Implement Cache Control headers in your Spring Boot application to prevent sensitive data from being stored in user’s browser cache.


## Solutions

### Configure Content Security Policy Header

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.headers()
      .contentSecurityPolicy("script-src 'self'; style-src 'self' https://maxcdn.bootstrapcdn.com");
  }
}
```
### Enable and Configure HSTS

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.headers()
      .httpStrictTransportSecurity()
      .includeSubDomains(true)
      .maxAgeInSeconds(31536000);
  }
}
```
### Disable Embedding of Your Application

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.headers()
      .frameOptions().deny();
  }
}
```
### Referrer Policy

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.headers()
      .referrerPolicy(ReferrerPolicy.NO_REFERRER);
}
}
```
### Content Type Options
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.headers()
      .contentTypeOptions();
  }
}
```
### Feature Policy
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.headers()
      .featurePolicy("camera 'self'; microphone 'self'");
  }
}
```
### Custom Headers
To add a custom header like X-Custom-Header: CustomValue, you might create a Filter that adds this header to each response.
```java
@Bean
public Filter customHeaderFilter() {
  return new Filter() {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      HttpServletResponse httpServletResponse = (HttpServletResponse) response;
      httpServletResponse.setHeader("X-Custom-Header", "CustomValue");
      chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
 };
}
```
### Conditionally Apply Headers
To conditionally apply headers, you might use a @Profile annotation or check the active profile during configuration. Here's a simplified example using @Profile:

```java
@Configuration
@EnableWebSecurity
@Profile("prod")  // This configuration will only be active in a "prod" profile
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//... (security configurations for production)
}

```
### Handle CORS
Here's a simple example of allowing a specific domain for CORS while restricting others.

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
      .allowedOrigins("https://allowed-origin.com")
      .allowedMethods("GET", "POST", "PUT", "DELETE");
  }
}
```
### Cache Control
Using Spring Security's headers configuration for cache control:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.headers()
      .cacheControl(CacheControl.noStore().mustRevalidate());
  }
}
```
