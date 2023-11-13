# CSRF

Cross-Site Request Forgery (CSRF) is a type of attack that tricks the victim into submitting a malicious request. It leverages the identity and privileges of the victim to perform an undesired function on their behalf.

### How CSRF Works:
- The victim logs into a web application.
- The web application stores a session cookie in the victim's browser.
- The attacker sends a malicious link or embeds malicious HTML or JavaScript code in a webpage viewed by the victim.
- The victim’s browser executes the malicious request with the authenticated session cookie.
- The web application performs the action requested by the attacker using the victim’s authenticated session.

A successful CSRF attack can lead to unauthorized actions being performed, such as changing account settings, performing transactions, or any other actions the victim has permissions to perform.

### Prevention in Spring Security:
Spring Security offers built-in protection against CSRF attacks, which is enabled by default. Here’s how you can customize CSRF protection in Spring Security:


```java
@Override
protected void configure(HttpSecurity http) throws Exception {
http
  .csrf()
  .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
}
```
In this example, the CookieCsrfTokenRepository.withHttpOnlyFalse() method is creating a CSRF token repository which will store the CSRF token in a cookie that can be read by JavaScript.

#### CSRF Token:
A CSRF token is a unique, random value associated with a user’s session and is used to verify the integrity of incoming requests. It's typically included as a hidden field in forms and sent as a HTTP header in AJAX requests. The server validates the token against the one stored in the user's session.

### Other Prevention Techniques:
- `SameSite Cookie Attribute`:    
Set the SameSite attribute on cookies to prevent the browser from sending cookies in cross-site requests.
- `Referer and Origin Headers`:   
Validate the Referer and Origin headers in HTTP requests to ensure requests are only accepted from trusted domains.
- `User Interaction`:   
Require user interaction, such as re-authentication or a confirmation button, for sensitive actions.
- `Anti-CSRF Tokens`:   
Embed anti-CSRF tokens in forms and links and validate them on the server side before processing requests.
- `Logging Out`:   
Encourage users to log out when they are done using the application, especially on shared computers.