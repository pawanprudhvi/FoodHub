package FoodHub.Hub.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;

@Service
public class jwtService {

    @Value("${Jwt.Secret.Key}")
    private String secretKeyEncoded;

    private Key signingKey;

    @Autowired
    SecretKeyProvider secretkeyprovider;

    public String getEmailId(String jwtToken) throws Exception {
       return extractClaim(jwtToken,Claims::getSubject);

    }
    private Claims getClaims(String jwtToken) throws Exception {
        return Jwts.parserBuilder().setSigningKey(getSigingKey()).build().parseClaimsJws(jwtToken).getBody();
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimResolver) throws Exception {
        Claims claims=getClaims(token);
        System.out.println("Successfully came here");
        return claimResolver.apply(claims);
    }

    private Key getSigingKey() throws Exception {
        byte[] key= Decoders.BASE64.decode(secretKeyEncoded);
        return Keys.hmacShaKeyFor(key);

    }


    public boolean isTokenExpired(String jwtToken) throws Exception {
       if(extractExpiration(jwtToken).before(new Date()))
       {
           return false;
       }
       return true;
    }

    private Date extractExpiration(String jwtToken) throws Exception {
        return extractClaim(jwtToken,Claims::getExpiration);

    }

    public boolean isValidated(String token, UserDetails username) throws Exception {
       String user=this.getEmailId(token);
        return isTokenExpired(token) && user.equals(username.getUsername());
    }
}
