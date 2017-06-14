package com.github.ssl.jvm;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;

public class Client {
    public static void main(String[] args) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(Client.class.getResourceAsStream("server.p12"), "changeit".toCharArray());
        SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, null).build();
        HttpClient httpClient = HttpClients.custom().setSslcontext(sslContext).build();
        HttpResponse response = httpClient.execute(new HttpGet("https://localhost:8443"));
        InputStream content = response.getEntity().getContent();
        String responseText = IOUtils.toString(content, "UTF-8");
        System.out.println(responseText);

    }
}
