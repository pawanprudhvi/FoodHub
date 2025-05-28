package FoodHub.Hub;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Base64;

@SpringBootApplication
public class HubApplication {

	public static void main(String[] args){
		SpringApplication.run(HubApplication.class, args);
		byte[] key = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
		String base64Key = Base64.getEncoder().encodeToString(key);
		System.out.println("Your secret key:\n" + base64Key);
	}

}
