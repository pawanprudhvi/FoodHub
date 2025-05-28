package FoodHub.Hub.JwtUtil;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;

import static javax.crypto.Cipher.SECRET_KEY;


@Component
public class JwtUtil
{
    @Value("${Jwt.Secret.Key}")
    private String SECRET_KEY;

    public String getSecretKey()
    {
        return SECRET_KEY;
    }

    @Autowired
    jwtService jwtService;

    @Autowired
    SecretKeyProvider secretkeyprovider;


    public String generateToken(UserDetails user,String role) throws Exception {
        HashMap<String,Object> claims = new HashMap<>();
        claims.put("role",role);
        return createToken(claims,user.getUsername());
    }

    private String createToken(HashMap<String, Object> claims, String username) throws Exception {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+1000*10*10*10))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }
}

