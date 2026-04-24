/*
 * Bober Clinic note: Contains code or settings for RSAKeyRecord.java.
 * File: backend-main/backend-main/src/main/java/boberbackend/config/RSAKeyRecord.java
 */
package boberbackend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "jwt")
public record RSAKeyRecord(RSAPublicKey rsaPublicKey, RSAPrivateKey rsaPrivateKey) {

}



