/*
 * Bober Clinic note: Starts the Bober Clinic backend server.
 * File: backend-main/backend-main/src/main/java/boberbackend/BoberBackendApplication.java
 */
package boberbackend;

import boberbackend.config.RSAKeyRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RSAKeyRecord.class)
@SpringBootApplication
public class BoberBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoberBackendApplication.class, args);
	}

}



