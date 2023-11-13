# Architecture

- Authentication:    
This is the process of establishing a principal is who they claim to be (a "principal" generally means a user, system, or some other actor).
- Authorization:     
Once authentication is established, authorization is the act of deciding whether a principal is allowed to perform an action within your application.

## Components
Here’s an overview of the key components of Spring Security's architecture:

#### SecurityContextHolder:
Holds the details of the currently authenticated user, i.e., the Authentication object.
#### Authentication Object:
Represents the principal. It contains the principal (current user's username), credentials (usually password), and a collection of granted authorities.
#### AuthenticationManager and ProviderManager:
Core authentication mechanism. It is responsible for verifying the authenticity of a principal. It delegates to one or more AuthenticationProvider implementations to perform the actual authentication against various sources (like databases, LDAP, custom stores, etc.).
#### UserDetailsService:
Used by the DaoAuthenticationProvider to retrieve user details from a specified source (DB, LDAP, etc.).
#### PasswordEncoder:
Used for encoding passwords for safer storage and comparison.
#### FilterChainProxy:
A collection of Spring Security filters that handles web security. Each filter is used for a specific security concern.
#### Security Filters:
The filters in the FilterChainProxy handle tasks like authentication (UsernamePasswordAuthenticationFilter), authorization (FilterSecurityInterceptor), and handling of session-related concerns (SessionManagementFilter).
#### AccessDecisionManager:
Used by the FilterSecurityInterceptor to make authorization decisions based on the authentication and the configured security metadata.
#### SecurityMetadataSource:
Provides metadata (like URL patterns and method annotations) which are secured and their corresponding roles or access rules.
#### RememberMeServices:
Provides “remember me” functionality, allowing a user's session to be remembered between browser sessions.
#### EntryPoint and AccessDeniedHandler:
Defines what to do when a user tries to access a protected resource without being authenticated or is authenticated but doesn’t have the required authority.
#### SessionManagement:
Handles session-related functionalities such as session fixation protection, concurrency control, etc.
#### CSRF Protection:
Spring Security provides built-in protection against Cross-Site Request Forgery attacks.
#### Method Security:
With annotations like @PreAuthorize, @PostAuthorize, and @Secured, you can secure methods at the service layer.
#### OAuth2 and OIDC:
Spring Security has first-class support for OAuth2 and OpenID Connect for building secure APIs and SSO capabilities.

### Flow:

- A user tries to access a protected resource.
- The request is intercepted by the `FilterChainProxy`.
- The `FilterChainProxy` delegates the request to the relevant filter (e.g., UsernamePasswordAuthenticationFilter for form login).
- If authentication is successful, the Authentication object is stored in the `SecurityContextHolder`.
- The user's request proceeds to the `FilterSecurityInterceptor` to check authorization.
- The `FilterSecurityInterceptor` uses the `AccessDecisionManager` to check if the authenticated user has the right authorities to access the resource.
- If access is granted, the request proceeds; otherwise, an exception is thrown.