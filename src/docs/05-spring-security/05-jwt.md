# Json Web Token

[https://jwt.io](https://jwt.io)

## The process 

- a user logs in with their credentials
- The server then generates a JWT token
- if the login is successful and sends it back to the client.
- Instead of storing the user's credentials, the client stores this token and sends it along with every request to the server.
- The server then verifies the token and processes the request.

This is particularly useful in single sign-on (SSO) scenarios as the token can be easily used to access multiple services.

## key components of a JWT

A JSON Web Token (JWT) is a compact and self-contained way for securely transmitting information between parties as a JSON object.    
This information can be verified and trusted because it is digitally signed.     
JWTs can be signed using a secret (with the HMAC algorithm) or a public/private key pair using RSA or ECDSA.


-  Header     
The header typically consists of two parts: the type of the token, which is JWT, and the signing algorithm being used, such as HMAC SHA256 or RSA.

- Payload    
The second part of the token is the payload, which contains the claims. Claims are statements about an entity (typically, the user) and additional data. There are three types of claims: registered, public, and private claims.

- Registered claims     
These are a set of predefined claims which are not mandatory but recommended, to provide a set of useful, interoperable claims. Some of them are: iss (issuer), exp (expiration time), sub (subject), aud (audience), and others.

- Public claims     
These can be defined at will by those using JWTs. But to avoid collisions they should be defined in the IANA JSON Web Token Registry or be defined as a URI that contains a collision resistant namespace.

- Private claims     
These are the custom claims created to share information between parties that agree on using them and are neither registered nor public claims.

- Signature     
To create the signature part you have to take the encoded header, the encoded payload, a secret, the algorithm specified in the header, and sign that.

- Encoding     
The output is three Base64-URL strings separated by dots that can be easily passed in HTML and HTTP environments, while being more compact when compared to XML-based standards such as SAML.

