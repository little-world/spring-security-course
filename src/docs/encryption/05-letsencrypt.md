# Let's Encrypt

To set up Let's Encrypt with Spring Boot, you typically use Certbot to obtain the certificate and then configure Spring Boot to use that certificate. Here's a high-level overview of the process:

#### Install Certbot
   Install Certbot on your server, which will be used to obtain and renew Let's Encrypt certificates. You can follow the installation instructions provided on the Certbot website.

#### Obtain a Certificate
   Once Certbot is installed, run it to obtain a certificate. You can do this by running the following command:

```text
sudo certbot certonly --standalone -d example.com -d www.example.com
```
Replace example.com and www.example.com with your domain name. The --standalone option is used to obtain a certificate by using a "standalone" web server, and it's necessary to stop your web server temporarily when you use this option.

#### Convert Certificate to PKCS 12 Format
   Spring Boot requires the certificate to be in PKCS 12 format. By default, Certbot generates PEM files, so you need to convert them. Navigate to the directory where the certificates are located (usually /etc/letsencrypt/live/yourdomainname/) and run the following command:

```text
sudo openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out keystore.p12 -name tomcat -CAfile chain.pem -caname root
```
You will be prompted to set a password for the keystore.


#### Configure Spring Boot to Use the Certificate
After converting the certificate to PKCS 12 format, configure your Spring Boot application to use it by modifying your application.properties or application.yml file.

Using application.properties:

```text
server.port=8443
server.ssl.key-store=/path-to-your/keystore.p12
server.ssl.key-store-type=PKCS12
server.ssl.key-store-password=yourkeystorepassword
server.ssl.key-alias=tomcat
```


#### Schedule Renewals
   Let's Encrypt certificates are valid for 90 days, but it's recommended to renew them every 60 days. Certbot can automate this process. To test the renewal process, you can run:

```text
sudo certbot renew --dry-run
```
If that works, you can add a cron job or a systemd timer to run sudo certbot renew twice a day.

#### Restart Your Spring Boot Application
   Finally, restart your Spring Boot application for the changes to take effect.

### Notes:
This guide assumes that you have full control over your server and domain to install Certbot and configure certificates.

It's essential to secure the keystore password and other sensitive configurations properly.

The port 80 needs to be free when running Certbot with the --standalone option, so you may need to stop your web server temporarily.

If you're using a web server or a proxy server like Apache or Nginx in front of your Spring Boot application, you might prefer to set up SSL/TLS termination at the web server/proxy server level instead of at the Spring Boot application level.