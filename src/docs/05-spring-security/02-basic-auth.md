# Basic Auth
Basic authentication is one of the simplest forms of HTTP authentication, where user credentials are sent with each HTTP request. The credentials are transmitted as plain Base64 encoded strings, so Basic authentication is not secure without additional layers like HTTPS. In Spring Security, setting up Basic authentication is quite straightforward.

Here's a step-by-step flow of how Basic Authentication works with Spring Security:

#### Client Sends Request:   
The client sends an HTTP request to a secured endpoint without any authentication credentials.
#### Server Responds with 401 Unauthorized:   
Spring Security intercepts the request and checks for authentication credentials.
Since no credentials are provided, Spring Security responds with an `HTTP 401 Unauthorized` status code and a `WWW-Authenticate` header set to Basic.
#### Client Sends Request with Credentials:   
After receiving the 401 response, a client (like a web browser) might prompt the user to enter their username and password.
The client then resends the same request, but this time with an `Authorization` header. The header contains the word Basic followed by a Base64 encoded string of the username and password in the format username:password.
#### Server Attempts Authentication: 

- Spring Security's `BasicAuthenticationFilter` intercepts the request and extracts the Base64 encoded credentials from the Authorization header.
- The credentials are decoded to retrieve the username and password.
- The `AuthenticationManager` tries to authenticate the user using the provided username and password. Typically, this involves:
- Loading user details (like roles and authorities) from the configured `UserDetailsService`.
- Comparing the provided password with the stored password. If using a password encoder, the provided password is encoded first and then compared.

#### Successful Authentication:   
If authentication is successful, an `Authentication` object is created, populated with the user's details, roles, and other credentials.
This Authentication object is then stored in the `SecurityContextHolder` to indicate the current user's authentication status.
The request is then forwarded to the destination endpoint, and processing continues as normal.
#### Failed Authentication:   
If the authentication process fails (due to incorrect credentials or other reasons), the server responds with an HTTP 401 Unauthorized status code.
The client might prompt the user to enter their credentials again or handle the failure in some other way. 
#### Subsequent Requests:    
For subsequent requests to secured endpoints, the client should continue sending the Authorization header with the Base64 encoded credentials. Since HTTP is stateless, and Basic Authentication doesn't involve sessions or tokens (like in form login or JWT), the credentials must be sent with each request to secured resources.
#### Logging Out:    
Since Basic Authentication is stateless, there's no traditional "logout" process. To "logout," the client simply stops sending the Authorization header.     
To force re-authentication, the client-side session or cache storing the credentials needs to be cleared.