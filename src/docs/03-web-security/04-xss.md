# XSS
The primary security header used to protect against Cross-Site Scripting (XSS) attacks is the Content-Security-Policy (CSP) header. It allows web developers to control the resources a user agent is allowed to load for a given page, thus helping in reducing or eliminating the vectors by which XSS can occur.

### Content-Security-Policy (CSP) Header:
CSP enables you to define and control the sources from which content like scripts, styles, images, etc., are loaded, preventing the execution of malicious content injected by attackers. For example, a strict CSP header may restrict the browser to load scripts only from the site's own domain and not from any other sources.

Here is an example of a strict CSP header:

```text
Content-Security-Policy: default-src 'self'; script-src 'self'; object-src 'none'; style-src 'self'; img-src 'self'; media-src 'none'; frame-src 'none';
```
In this example:

- `default-src 'self'`: Only load resources (scripts, styles, images, etc.) from the same origin.
- `script-src 'self'`: Only load scripts from the same origin.
- `object-src 'none'`: Do not load objects (like <object>, <embed>).
- `style-src 'self'`: Only load styles from the same origin.
- `img-src 'self'`: Only load images from the same origin.
- `media-src 'none'`: Do not load media (audio, video).
- `frame-src 'none'`: Do not load frames (iframes).

## Spring Security

### Input Validation and Sanitization:
   Validate all incoming data to ensure it conforms to expected formats.
   Sanitize user inputs to remove any potentially malicious content.
   Use libraries like OWASP Java HTML Sanitizer to sanitize inputs and outputs.

```java
   import org.owasp.html.HtmlPolicyBuilder;
   import org.owasp.html.PolicyFactory;

PolicyFactory policy = new HtmlPolicyBuilder().toFactory();
String safeHTML = policy.sanitize(userInput);
```

### Output Encoding:
   When displaying user-generated content, ensure that it's properly escaped to prevent the injection of malicious scripts.
   Use encoding functions available in templating engines like Thymeleaf to automatically escape output data.
  
```html
<!-- In Thymeleaf, the following automatically escapes data -->
<span th:text="${userInput}"></span>
```

### Implement Content-Security-Policy (CSP):
   Configure your application to send the Content-Security-Policy header with appropriate values to restrict the sources from which content can be loaded.
   Use Spring Security’s ContentSecurityPolicyHeaderWriter to set up a strict policy.
```java
   http
   .headers()
   .contentSecurityPolicy("default-src 'self'")
```