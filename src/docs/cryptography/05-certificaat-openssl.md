# OpenSSL
OK, hier gaan we het stap voor stap doen (dit is een test):

```text
mkdir ssl
cd ssl
```
### Maak een sleutel:

```text
openssl genrsa -des3 -out server.key 1024
 ```

Hiermee genereren we een key die meteen beveiligd wordt
met een blockcipher, hier triple des (probeer ook aes256)
voor rijndahl beveiliging.
OK we hebben nu een server.key, dit is een private key
voor de server. Die ziet er zo uit

```text
more server.key
```
### Bekijken
Nu kunnen we ook even bekijken hoe die key eruit ziet,
maw wat staat erin:

```text
openssl rsa -noout -text -in server.key
```
Je kunt nu ook naar de echte (unencrypted) versie
van de sercret key kijken


### Maak Certificaat 
Nu willen een certificerings verzoek opstellen. Hiertoe moeten
we een aantal vragen beantwoorden, die in dit verzoek worden
opgenomen, en dan worden ondertekend door de authoriteit, die
natuurlijk wel zou moeten controleren of dit allemaal juist is.

```text
openssl req -new -key server.key -out server.csr
```
Dit genereert een server.csr file waarin deze zaken zijn opgenomen.
Kijk maar eens even:
```text
openssl req -noout -text -in server.csr
```
Tot hier is het het stuk dat je altijd zelf kunt. Als je een
certificaat wilt dat door browsers wordt herkent, kun je dit
document, samen met geld, sturen  Verisign, Thawte etc etc.
Er zijn ook goedkopere.

### Maak Authority
We kunnen echter ook zelf CA worden Certifying Authority (CA)
worden. Dat gaan we nu behandelen.

Daarvoor moeten we weer een nieuwe private key aanmaken
```text
openssl genrsa -des3 -out ca.key 1024
```
Maak nu een Zelf - ondertekend  CA Certificate (X509 structure)
met de RSA key van de CA (in PEM formaat):

```text
openssl req -new -x509 -days 365 -key ca.key -out ca.crt
```
En ff kijken of dit gelukt is:x

```text
openssl x509 -noout -text -in ca.crt
```
Een (MD5) fingerprint van het certificate kan altijd worden
opgevraagd met
```text
openssl x509 -fingerprint -noout -in ca.crt
MD5 Fingerprint=40:50:A0:71:84:2A:C8:1E:DE:36:03:64:D5:91:38:69
```

Nu moeten we ons oorspronkelijke certificate gaan ondertekenen
met die van de CA.
```text
openssl x509 -req -days 365 -in server.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out server.crt
```
Dit levert een server certificaat af: server.crt
Laten we nu eens aan de binnenkant kijken:

```text
openssl x509 -noout -text -in server.crt
```

OK, we zien een certificaat uitgegeven door Beta Holding en
van Beta Research. Daarmee zegt Beta Holding dat de info
van Beta Research echt is.

### Formaten.
Je hebt het PEM en DER formaten voor het certificaat.
De bovenstaande formaten zijn in PEM. Converteren naar DER
gaat zo:
```text
openssl x509 -in test.crt -out test.der -outform
```
en zo ga je terug
```text
openssl x509 -in test.der -inform DER -out test.pem -outform PEM
```
test.pem en test.crt zijn identiek:
```text
md5sum test.crt
bf05a3cb5eef90582431418ab7916657  test.crt
md5sum test.pem
bf05a3cb5eef90582431418ab7916657  test.pem
```


Een DER vorm kan je niet gemakkelijk op het scherm bekijken.


Daarnaast heb je ook nog PKCS12 (en voorgangers ...) die je kunt
converteren.
" In general, the PEM formats are mostly used in the Unix world, PKCS12
in the Microsoft world and DER in the Java world. "

omzetten van pem certificate naar pkcs12 gaat als volgt
```text
openssl pkcs1pwd
2 -export -out usercert.p12 -inkey user.key -in user.crt
```

en terug (mbv twee commandoos)
```text
openssl pkcs12 -clcerts -nokeys -in usercert.p12 -out usercert.pem
openssl pkcs12 -nocerts -in usercert.p12 -out userkey.pem
```