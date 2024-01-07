
# Java Cryptography

To perform encryption and decryption in Java, you typically use the Java Cryptography Architecture (JCA) and Java Cryptography Extension (JCE). These provide a framework and implementations for encryption, key generation, key agreement, and message authentication code (MAC) algorithms.

Below is a simple example using Advanced Encryption Standard (AES) symmetric encryption, but be aware that proper encryption in a production environment usually requires more considerations (like secure key management, initialization vector handling, and more).


### Create a new Java class

Let's call it AESExample.java.

```java
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AESExample {

    public static void main(String[] args) throws Exception {
        // 1. Generate a Secret Key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // key size: 128 bits
        SecretKey secretKey = keyGenerator.generateKey();

        // 2. Encrypt a message
        String message = "This is a secret message";
        String encryptedMessage = encryptMessage(message, secretKey);
        System.out.println("Encrypted: " + encryptedMessage);

        // 3. Decrypt the message
        String decryptedMessage = decryptMessage(encryptedMessage, secretKey);
        System.out.println("Decrypted: " + decryptedMessage);
    }
    

    private static String encryptMessage(String message, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private static String decryptMessage(String encryptedMessage, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedMessage);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
```


- Generate a Secret Key:    
KeyGenerator is used to generate a secret symmetric key for AES encryption. Here, keyGenerator.init(128) is used to initialize key generator for 128-bit key size.
- Encrypt a Message:     
Convert the message to bytes and use Cipher to encrypt it, then encode the resulting byte array to Base64 string.
- Decrypt the Message:   
Decode the Base64 string to bytes and use Cipher to decrypt it, then convert the resulting byte array back to the original string.


###  Asymmetric Encryption
Here's a simplified example of how you might implement a system with RSA asymmetric encryption in Java.

#### Key Pair Generation

Generate a pair of RSA keys (public and private).
```java
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class AsymmetricEncryptionUtil {

  public static KeyPair generateRSAKeyPair() throws Exception {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(2048); // Key size
    return keyPairGenerator.generateKeyPair();
  }

  //... Other methods will go here
}
```
#### Encryption

Encrypt a message using the recipient’s public key.

```java
import javax.crypto.Cipher;
import java.util.Base64;

public class AsymmetricEncryptionUtil {
//... Previous methods

  public static String encrypt(String message, PublicKey publicKey) throws Exception {
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
    byte[] encryptedBytes = cipher.doFinal(message.getBytes());
    return Base64.getEncoder().encodeToString(encryptedBytes);
  }
}
```
#### Decryption

Decrypt a message using the recipient’s private key.

```java
import javax.crypto.Cipher;
import java.util.Base64;

public class AsymmetricEncryptionUtil {
//... Previous methods

  public static String decrypt(String encryptedMessage, PrivateKey privateKey) throws Exception {
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.DECRYPT_MODE, privateKey);
    byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
    return new String(decryptedBytes);
  }
}
```
#### Main Application

Let's assume Alice and Bob want to communicate securely. Alice encrypts the message with Bob’s public key, and Bob decrypts it with his private key.

```java
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class MainApp {
  public static void main(String[] args) {
    try {
      // Generate RSA key pairs for Alice and Bob
      KeyPair aliceKeyPair = AsymmetricEncryptionUtil.generateRSAKeyPair();
      KeyPair bobKeyPair = AsymmetricEncryptionUtil.generateRSAKeyPair();

      // Message that Alice wants to send to Bob
      String originalMessage = "Hello Bob!";

      // Alice encrypts the message using Bob's public key
      PublicKey bobPublicKey = bobKeyPair.getPublic();
      String encryptedMessage = AsymmetricEncryptionUtil.encrypt(originalMessage, bobPublicKey);
      System.out.println("Original Message: " + originalMessage);
      System.out.println("Encrypted Message: " + encryptedMessage);

      // Bob decrypts the message using his private key
      PrivateKey bobPrivateKey = bobKeyPair.getPrivate();
      String decryptedMessage = AsymmetricEncryptionUtil.decrypt(encryptedMessage, bobPrivateKey);
      System.out.println("Decrypted Message: " + decryptedMessage);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
```
In this example:

Alice sends a message to Bob.    
She encrypts the message using Bob’s public key so that only Bob can decrypt it using his private key.   
Bob receives the encrypted message and decrypts it using his private key.   
Ensure you store and transmit key pairs securely and comply with any applicable data protection regulations. Asymmetric encryption can be computationally expensive, especially for large data, so it’s often used to encrypt symmetric keys, which in turn encrypt actual data/messages.


## Exercises

### Basic Encryption/Decryption
Create a basic Java application where a user can input a string, which is then encrypted and decrypted using a securely generated key, as demonstrated in the previous examples.


### RSA Encryption and Decryption
Objective: Write a Java program to encrypt and decrypt a text using RSA algorithm.

Description:

- Create two methods: encryptTextUsingRSA and decryptTextUsingRSA.
- The encryptTextUsingRSA method should take plaintext as input and return the encrypted text.
- The decryptTextUsingRSA method should take the encrypted text and return the original plaintext.
- Generate a pair of private and public RSA keys.
- Use the public key for encryption and the private key for decryption.
- Handle NoSuchAlgorithmException and InvalidKeyException.
- You may use the Cipher class from javax.crypto for encryption and decryption.

Sample Code Structure:

```java
import javax.crypto.Cipher;
import java.security.*;

public class RSAEncryption {

    public static byte[] encryptTextUsingRSA(String text, PublicKey publicKey) {
        // Implement encryption logic here
    }

    public static String decryptTextUsingRSA(byte[] cipherText, PrivateKey privateKey) {
        // Implement decryption logic here
    }

    public static void main(String[] args) {
        // Generate RSA keys
        // Test encryption and decryption methods
    }
}
```

### File Encryption/Decryption

- Task A: Implement file encryption by reading the contents of a file, encrypting it, and then writing the encrypted contents back to a file.
- Task B: Implement file decryption by reading the encrypted contents of a file, decrypting it, and then writing the decrypted contents back to a file.

### Secure Password Storage
Create an application that securely stores and retrieves user passwords.

- Task A: Allow users to input a username and password.
- Task B: Hash and salt the password, and then store the username and hashed password.
- Task C: Allow users to retrieve the hashed password by inputting the correct username.

### Using Different Cipher Modes
Explore the effect of using different cipher modes in symmetric key encryption (such as ECB, CBC, CTR, GCM, etc.)

- Task A: Encrypt and decrypt a message using AES with ECB mode and display the ciphertext.
- Task B: Encrypt and decrypt a message using AES with CBC mode and display the ciphertext.
- Task C: Discuss or document the differences you observe between different modes.

### Data Integrity
Develop a system that ensures data integrity using HMAC (Hash-based Message Authentication Code).

- Task A: Create a function that generates HMAC for messages using a secret key.
- Task B: Send messages along with their HMAC and ensure they haven’t been tampered with by verifying the HMAC on the receiving side.
- Task C: Simulate message tampering and observe how the system detects it.


### Digital Signatures
Implement a system where messages are signed using digital signatures and verify the signature upon receipt.

- Task A: Generate a pair of RSA keys to be used for signing and verifying messages.
- Task B: Implement a function that signs messages using the sender's private key.
- Task C: Implement a function that verifies the signature using the sender’s public key.


## Solutions

### Basic Encryption/Decryption Solution
#### Key Generation

```java
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class CryptoUtil {
  public static SecretKey generateKey() throws Exception {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    keyGenerator.init(128); // Key size
    return keyGenerator.generateKey();
  }
// ... Other methods will go here
}
```
#### Encryption

```java
public class CryptoUtil {
// ... Previous methods

  public static String encrypt(String data, SecretKey secretKey) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] encryptedData = cipher.doFinal(data.getBytes());
    return Base64.getEncoder().encodeToString(encryptedData);
  }
}
```
#### Decryption

```java
public class CryptoUtil {
// ... Previous methods

  public static String decrypt(String encryptedData, SecretKey secretKey) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    byte[] originalData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
    return new String(originalData);
  }
}
```
#### Main Application

```java
import javax.crypto.SecretKey;

public class MainApp {
  public static void main(String[] args) {
    try {
// Generate secret key
      SecretKey secretKey = CryptoUtil.generateKey();

      // Data to encrypt
      String originalData = "SensitiveData";

      // Encrypt the data
      String encryptedData = CryptoUtil.encrypt(originalData, secretKey);
      System.out.println("Original Data: " + originalData);
      System.out.println("Encrypted Data: " + encryptedData);

      // Decrypt the data
      String decryptedData = CryptoUtil.decrypt(encryptedData, secretKey);
      System.out.println("Decrypted Data: " + decryptedData);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
```
This solution reflects the implementation of basic symmetric encryption/decryption using AES.   
The CryptoUtil class is defined to manage encryption-related operations like key generation, encryption, and decryption.    
The MainApp class demonstrates how to utilize these methods.



### RSA Encryption and Decryption
```java
import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

public class RSAEncryption {

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        return generator.generateKeyPair();
    }

    public static String encryptTextUsingRSA(String text, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherText = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decryptTextUsingRSA(String cipherText, PrivateKey privateKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(cipherText);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(bytes));
    }

    public static void main(String[] args) {
        try {
            KeyPair keyPair = generateKeyPair();

            String originalText = "Hello, RSA!";
            String encryptedText = encryptTextUsingRSA(originalText, keyPair.getPublic());
            String decryptedText = decryptTextUsingRSA(encryptedText, keyPair.getPrivate());

            System.out.println("Original Text: " + originalText);
            System.out.println("Encrypted Text: " + encryptedText);
            System.out.println("Decrypted Text: " + decryptedText);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}
```

### File Encryption/Decryption

#### File Encryption

```java
import javax.crypto.*;
import java.nio.file.*;
import java.util.Base64;

public class FileEncryption {

    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // Key size
        return keyGenerator.generateKey();
    }

    public static void encryptFile(Path inputFile, Path outputFile, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] inputBytes = Files.readAllBytes(inputFile);
        byte[] outputBytes = cipher.doFinal(inputBytes);

        Files.write(outputFile, Base64.getEncoder().encode(outputBytes));
    }

    // Main method and decryption method will go here...
}
```
#### File Decryption

```java
import javax.crypto.*;
import java.nio.file.*;
import java.util.Base64;

public class FileEncryption {
//... Previous methods

    public static void decryptFile(Path inputFile, Path outputFile, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] inputBytes = Files.readAllBytes(inputFile);
        byte[] decodedBytes = Base64.getDecoder().decode(inputBytes);
        byte[] outputBytes = cipher.doFinal(decodedBytes);

        Files.write(outputFile, outputBytes);
    }

    public static void main(String[] args) {
        try {
            SecretKey secretKey = generateKey();

            Path inputFile = Paths.get("path_to_input_file");
            Path encryptedFile = Paths.get("path_to_encrypted_file");
            Path decryptedFile = Paths.get("path_to_decrypted_file");

            encryptFile(inputFile, encryptedFile, secretKey);
            System.out.println("File encrypted successfully!");

            decryptFile(encryptedFile, decryptedFile, secretKey);
            System.out.println("File decrypted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
#### Explanation:

- Key Generation:    
generateKey generates an AES secret key of 128 bits.
- File Encryption:    
encryptFile reads all bytes from the input file, encrypts them using AES, encodes them with Base64, and writes them to the output file.
- File Decryption:    
decryptFile reads all bytes from the encrypted file, decodes them from Base64, decrypts them using AES, and writes them to the output file.
- Main Method:    
Generates a secret key.
Specifies paths for the input, encrypted, and decrypted files.
Calls encryptFile and decryptFile and outputs success messages.
This example operates with entire file contents in memory, which might not be suitable for large files. For larger files, you would need to implement streaming encryption/decryption, processing chunks of the file at a time, which would be considerably more complex but also more memory-efficient.




### Secure Password Storage Solution
In this exercise, we aim to create an application that securely stores and retrieves user passwords using hashing and salting techniques.

Task A, B: Allow users to input and store hashed/salted passwords

Task C: Allow users to retrieve the hashed password

```java
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SecurePasswordStorage {

  private static final Map<String, String> userPasswords = new HashMap<>();
  private static final Map<String, byte[]> userSalts = new HashMap<>();

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // User Registration
    System.out.println("User Registration:");
    System.out.print("Enter Username: ");
    String username = scanner.nextLine();
    System.out.print("Enter Password: ");
    String password = scanner.nextLine();

    // Generate Salt
    byte[] salt = generateSalt();
    userSalts.put(username, salt);

    // Store User and Hashed Password
    String hashedPassword = hashPassword(password, salt);
    userPasswords.put(username, hashedPassword);
    System.out.println("User registered successfully!\n");

    // Retrieval of hashed password
    System.out.println("Retrieve Hashed Password:");
    System.out.print("Enter Username: ");
    String enteredUsername = scanner.nextLine();

    if (userPasswords.containsKey(enteredUsername)) {
      System.out.println("Hashed Password: " + userPasswords.get(enteredUsername));
    } else {
      System.out.println("User does not exist.");
    }
  }

  private static byte[] generateSalt() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    return salt;
  }

  private static String hashPassword(String password, byte[] salt) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(salt);
      byte[] hashedPassword = md.digest(password.getBytes());
      return bytesToHex(hashedPassword);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  private static String bytesToHex(byte[] hash) {
    StringBuilder hexString = new StringBuilder();
    for (byte b : hash) {
      String hex = Integer.toHexString(0xff & b);
      if (hex.length() == 1) hexString.append('0');
      hexString.append(hex);
    }
    return hexString.toString();
  }
}
```
#### Explanation:

- `generateSalt()`:    
Generates a random salt using SecureRandom.
- `hashPassword(String password, byte[] salt)`: 
Computes the SHA-256 hash of the password concatenated with the salt. SHA-256 is chosen here as a commonly used secure hash function. Remember that more iterations and a key derivation function like PBKDF2 would be even more secure.
- `bytesToHex(byte[] hash)`:    
Converts the byte array to a hexadecimal string since the hashed password is in bytes.

The main method orchestrates the process:

- Takes username and password input.
- Generates a salt and hashes the password with the salt.
- Stores the username and hashed password in userPasswords, and the username and salt in userSalts.
- Allows retrieval of hashed passwords using the username.

This basic example demonstrates the principles of securely storing passwords. However, security is a deep and broad field, and building a secure system involves comprehensive considerations like using well-vetted authentication frameworks, secure communication (HTTPS), etc. Always keep abreast of best practices and current recommendations for password storage and management in production systems.


### Implementing Symmetric Encryption with User Input
In this solution, we’ll build a simple Java application that allows users to input a message, then encrypts and decrypts the message using symmetric encryption (AES).

#### AES Utility Class

This class will provide utility methods for key generation, encryption, and decryption using AES symmetric encryption.

```java
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class AESUtil {

  public static SecretKey generateAESKey(int size) throws Exception {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    keyGenerator.init(size); // Key size: 128, 192, or 256 bits
    return keyGenerator.generateKey();
  }

  public static String encrypt(String message, SecretKey key) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, key);
    byte[] encryptedBytes = cipher.doFinal(message.getBytes());
    return Base64.getEncoder().encodeToString(encryptedBytes);
  }

  public static String decrypt(String encryptedMessage, SecretKey key) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.DECRYPT_MODE, key);
    byte[] originalBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
    return new String(originalBytes);
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    try {
      // Key Generation
      SecretKey secretKey = generateAESKey(128);

      // User Input
      System.out.println("Enter a message to encrypt: ");
      String originalMessage = scanner.nextLine();

      // Encryption
      String encryptedMessage = encrypt(originalMessage, secretKey);
      System.out.println("Original Message: " + originalMessage);
      System.out.println("Encrypted Message: " + encryptedMessage);

      // Decryption
      String decryptedMessage = decrypt(encryptedMessage, secretKey);
      System.out.println("Decrypted Message: " + decryptedMessage);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      scanner.close();
    }
  }
}
```
#### Explanation:

- `generateAESKey(int size)`:   
Generates a symmetric AES key of the specified size.
- `encrypt(String message, SecretKey key)`:
Encrypts the provided message using the given AES key and returns the encrypted string.
- `decrypt(String encryptedMessage, SecretKey key)`:   
Decrypts the provided encrypted message using the given AES key and returns the original string.

The main method orchestrates the process:

- Generates an AES key.
- Accepts a message from the user.
- Encrypts and then decrypts the message, displaying each to the console.

This solution guides through a basic demonstration of AES encryption in a Java application. Ensure to handle exceptions properly and securely manage keys in a production environment. Always consult cybersecurity expertise for creating security-sensitive applications.




### Data Integrity


#### imports

```java
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
```

#### Function that generates HMAC

```java
public class HMACSystem {

  public static String generateHmac(String message, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
    Mac mac = Mac.getInstance("HmacSHA256");
    SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
    mac.init(secretKeySpec);
    byte[] hmacBytes = mac.doFinal(message.getBytes());
    return Base64.getEncoder().encodeToString(hmacBytes);
  }
}
```

#### Send and Verify Messages with HMAC

```java
// ... continuation of HMACSystem class ...

public static void sendMessage(String message, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
  String hmac = generateHmac(message, secretKey);
  System.out.println("Sending message: " + message);
  System.out.println("HMAC: " + hmac);
  receiveMessage(message, hmac, secretKey);
}

public static void receiveMessage(String message, String hmac, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
  String calculatedHmac = generateHmac(message, secretKey);
  if (calculatedHmac.equals(hmac)) {
    System.out.println("Received message: " + message);
    System.out.println("Message is verified and not tampered.");
  } else {
    System.out.println("Received message: " + message);
    System.out.println("Message is tampered or HMAC is invalid!");
  }
}
```
#### Simulate Tampering

```java
// ... continuation of HMACSystem class ...

public static void main(String[] args) {
  try {
  String secretKey = "SuperSecretKey"; // This should be kept secret and ideally generated securely
  String originalMessage = "Hello, World!";
  
  // Normal scenario
  System.out.println("\n=== Sending Original Message ===");
  sendMessage(originalMessage, secretKey);
  
  // Tampering scenario
  System.out.println("\n=== Sending Tampered Message ===");
  String tamperedMessage = "Hello, Universe!";
  receiveMessage(tamperedMessage, generateHmac(originalMessage, secretKey), secretKey);
  
  } catch (Exception e) {
    e.printStackTrace();
  }
}

```
Here's what's happening in the provided solution:

- `generateHmac`:    
Generates an HMAC for a given message using a given secret key.
- `sendMessage`:     
Simulates the sending of a message. It computes the HMAC of the message, displays it, then calls receiveMessage to simulate message reception and verification.
- `receiveMessage`:    
Simulates the reception of a message. It computes the HMAC of the received message and compares it to the received HMAC to verify the message's integrity.
- `main`:     
Demonstrates the entire process. First, it sends and verifies an original, unaltered message. Then, it simulates a tampered message being sent and shows the verification failure.

When running the main method, you should see that the original message is verified successfully, while the tampered message fails verification, showcasing the utility of HMAC in ensuring data integrity

### Implementing Digital Signatures in Java
Let's go through the solution step by step:


```java
import java.security.*;
import java.util.Base64;

public class DigitalSignatureSystem {

  // Task A: Generate RSA Key Pair
  public static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(2048);
    return keyPairGenerator.generateKeyPair();
  }

  // Task B: Sign Message
  public static String signMessage(String message, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    Signature signature = Signature.getInstance("SHA256withRSA");
    signature.initSign(privateKey);
    signature.update(message.getBytes());
    byte[] signatureBytes = signature.sign();
    return Base64.getEncoder().encodeToString(signatureBytes);
  }

  // Task C: Verify Message
  public static boolean verifyMessage(String message, String signatureData, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    Signature signature = Signature.getInstance("SHA256withRSA");
    signature.initVerify(publicKey);
    signature.update(message.getBytes());
    byte[] signatureBytes = Base64.getDecoder().decode(signatureData);
    return signature.verify(signatureBytes);
  }

  public static void main(String[] args) {
    try {
      String originalMessage = "Hello, this is a secret message!";

      // Task A: Generate RSA keys
      KeyPair keyPair = generateRSAKeyPair();

      // Task B: Sign message
      String signature = signMessage(originalMessage, keyPair.getPrivate());
      System.out.println("Original Message: " + originalMessage);
      System.out.println("Generated Signature: " + signature);

      // Task C: Verify signature
      boolean isVerified = verifyMessage(originalMessage, signature, keyPair.getPublic());
      System.out.println("Signature Verified: " + isVerified);

      // Simulating tampered message
      String tamperedMessage = "Hello, this is NOT a secret message!";
      boolean isTamperedVerified = verifyMessage(tamperedMessage, signature, keyPair.getPublic());
      System.out.println("\nTampered Message: " + tamperedMessage);
      System.out.println("Signature Verified: " + isTamperedVerified);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
```

#### Explanation:
- `generateRSAKeyPair()`:    
Generates a pair of RSA keys.
- `signMessage(String message, PrivateKey privateKey)`:    
Signs the message with a private RSA key and returns a string representation of the signature.
- `verifyMessage(String message, String signatureData, PublicKey publicKey)`:    
Verifies the signature of the message using the public RSA key and returns a boolean indicating whether the signature is valid.

- `main` method:
- - An RSA key pair is generated.
- - An original message is signed using the private key.
- - The signature is verified using the public key.
- - A tampered message is attempted to be verified, which should fail.

Running the main method should demonstrate the signing and verification process and also how the system detects a tampered message by failing to verify its signature.









