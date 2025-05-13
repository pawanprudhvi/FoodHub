package FoodHub.Hub.JwtUtil;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Setter
@Getter
@Component
public class SecretKeyProvider {
    private String base64Key;

    public SecretKeyProvider(String base64Key) {
        this.base64Key = base64Key;
    }
    public SecretKeyProvider()
    {

    }
    @PostConstruct
    public void init()
    {
        byte[] key = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
         this.base64Key = Base64.getEncoder().encodeToString(key);
        System.out.println("Your secret key:\n" + base64Key);
    }

    public String getBase64Key() {
        return base64Key;
    }
}
