# Purpose

The purpose of this repo is to provide SSL for JVM example keeping it as simple as possible.

# How to use

### Starting server

```bash
./gradlew :server:run
```

### Starting client
```bash
./gradlew :client:run -PappArgs="['../test.p12', 'changeit']" -q
```

# Commands

### Generate server key pair (PKCS#12)
```bash
keytool -genkey -alias localhost -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore server.p12 -validity 3650
```

### Generate test (client) certificate (PKCS#12)
```bash
keytool -genkey -alias test -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore test.p12 -validity 3650
```

### Extract public key from client certificate (DER)
```bash
openssl pkcs12 -in test.p12  -clcerts -nokeys -out test.crt
```

### Add public key to server truststore
```bash
keytool -importcert -file test.crt -keystore truststore.jks -alias test
```
