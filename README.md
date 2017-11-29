# Purpose

The purpose of this repo is to provide SSL for JVM example keeping it as simple as possible.

# How to use

## CA

We trust CA which is located in directory `ca` - because we have to trust someone :)
CA signs CSRs using script `./ca/sign_by_ca.sh`.
Usage:
```
./ca/sign_by_ca.sh csr.file
```

## Server

### Generate server key

```bash
openssl genrsa -aes256 -out server.key 4096
```

### Generate server CSR

```bash
openssl req -new -sha256 -key server.key -out server.csr
> Common Name (e.g. server FQDN or YOUR name) []:localhost 
```

### Sign with CA

```bash
./ca/sign_by_ca.sh server.csr
```

### Merge certificate and private key

```bash
openssl pkcs12 -export -in server.crt -inkey server.key -out server.p12
```

### Copy crt to application

```bash
cp server.p12 server/src/main/resources/
```

### Set cert password (`server/src/main/resources/application.properties`)

```properties
server.ssl.key-store-password=
```

### Import CA file to truststore

```bash
keytool -import -keystore truststore.jks -alias ca -file ca/ca.crt
```

## Client

### Generate client key

```bash
openssl genrsa -aes256 -out client.key 4096
```

### Generate client CSR
```bash
openssl req -new -sha256 -key client.key -out client.csr
```

### Sign client 
```bash
./ca/sign_by_ca.sh client.csr
```

### Build PKCS#12 certificate
```bash
openssl pkcs12 -export -in client.crt -inkey client.key -out client.p12
```

## Testing

### Run server
```bash
./gradlew :server:run
```

### Run client (in another terminal)
```bash
./gradlew :client:run -PappArgs="['../client.p12', 'YOUR PASSWORD HERE']" -q
```
