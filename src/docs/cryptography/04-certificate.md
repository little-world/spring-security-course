# Certificate

Certificates in the context of computer security are digital documents used to verify the identity of individuals, computers, and other entities on a network. They play a crucial role in various aspects of information security, including SSL/TLS for secure web browsing, code signing, secure email, and more.

### key concepts

- `Public Key Infrastructure (PKI):`
PKI is the framework used to manage digital certificates and public-key encryption.
It enables secure data exchange by providing each entity with a pair of cryptographic keys: a public key and a private key.
- `Digital Certificates:`   
A digital certificate is an electronic document that uses a digital signature to bind a public key with an identity — this can be an individual, a computer, or an organization.
The certificate includes information about the key, identity, and the digital signature of an entity that has verified the certificate's contents.
It's used to confirm that a public key belongs to the specific individual, organization, or device.
- `Certificate Authority (CA)`       
A CA is a trusted entity that issues digital certificates.
The CA verifies the identity and legitimacy of the certificate requestor before issuing a certificate.
CAs play a critical role in how the internet operates and how transparent, trusted transactions can occur online.
- `Certificate Validation`      
When a browser or other client device encounters a certificate, it checks to ensure the certificate is valid and trusted.
The client validates the certificate's expiration date, the integrity of its signature, and its chain of trust (i.e., whether it was issued by a trusted CA).
- `Chain of Trust`    
A series of certificates, each certifying the next, linking an end-user certificate to a root trust anchor (typically a root CA certificate).
The chain of trust is a critical component of certificate trustworthiness.
- `Revocation and Expiration`
Certificates are issued with an expiration date, after which they are no longer valid.
If a certificate is compromised before its expiration date, it can be revoked. Clients checking this certificate will see that it is no longer trusted.

#### Use Cases:
- `HTTPS`: Certificates are used to secure web browsing sessions.
- `Secure Email`: Certificates are used to encrypt email content.
- `Code Signing`: Certificates are used to sign software and scripts to verify that they have not been tampered with.

Certificates are foundational to establishing a secure, trustworthy, and encrypted internet. They enable users to confidently engage in digital transactions, knowing that their information is secure and that the entities they are interacting with are who they claim to be.

### Create certificate
Creating a web certificate involves creating a Certificate Signing Request (CSR), having it signed by a Certificate Authority (CA), and then installing it on your web server. You can create a self-signed certificate for development and testing purposes, but for production, you should get your certificate signed by a trusted CA.

### Creating a Self-Signed Certificate using Java’s keytool
Java provides a command-line utility called keytool that allows you to work with keystores, generate CSRs, and create self-signed certificates.

#### Create a Keystore and a Key Pair

```text
keytool -genkeypair -keyalg RSA -keysize 2048 -keystore mykeystore.jks -alias myalias
```
- `-keyalg` RSA specifies the key algorithm.
- `-keysize` 2048 specifies the size of the key.
- `-keystore mykeystore`.jks specifies the name of the keystore.
- `-alias myalias` specifies the alias for the key pair.
- 
You will be prompted to enter information about the certificate and a password for the keystore and the key pair.

#### Create a Self-Signed Certificate

```text
keytool -exportcert -keystore mykeystore.jks -alias myalias -file mycert.crt
```

- `-file mycert.crt` specifies the name of the file to which the certificate will be exported.

- You will need to enter the keystore password you set in the previous step.

Now you have created a self-signed certificate (mycert.crt) and a keystore (mykeystore.jks) containing your private key.


### Spring Boot
When using Spring Boot, you typically configure SSL/TLS in the application's properties file (application.properties or application.yml) using the created keystore. Here’s how you can do it.

Using application.properties
```text
server.port=8443
server.ssl.key-store-type=JKS
server.ssl.key-store=path/to/your/keystore.jks
server.ssl.key-store-password=yourkeystorepassword
server.ssl.key-alias=yourkeyalias
```

- `server.port`: Specifies the port on which your server should run. Port 8443 is the conventional port for HTTPS.
- `server.ssl.key-store-type`: Specifies the type of the keystore, typically JKS for Java Keystore.
- `server.ssl.key-store`: Specifies the path to your keystore file.
- `server.ssl.key-store-password`: Specifies the password of your keystore file.
- `server.ssl.key-alias`: Specifies the alias of the key in your keystore.

### Important Points:
Ensure that the keystore path is correct, and the keystore password and key alias are correctly set in the properties or YAML file. 

When deploying the application, make sure to secure your configuration, especially properties or YAML files that contain sensitive information like passwords.

For development and testing purposes, you can use a self-signed certificate, but for production environments, always use a certificate issued by a trusted Certificate Authority.

Make sure to restart your Spring Boot application after modifying the application.properties or application.yml file for the changes to take effect.
If your application is running behind a reverse proxy or a load balancer, additional configuration might be needed to handle SSL/TLS termination properly.

Testing:
Once you have configured SSL/TLS in your Spring Boot application, you can test it by accessing the application via HTTPS, like so:

```text
https://localhost:8443
```
Replace localhost and 8443 with your server's domain name or IP address and port if they are different.