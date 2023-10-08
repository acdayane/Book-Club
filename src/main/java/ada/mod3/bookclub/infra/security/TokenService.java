package ada.mod3.bookclub.infra.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import ada.mod3.bookclub.model.User;

@Service
public class TokenService {

    @Value("${config.token.secret}")
    private String secretKey;
    
    public String tokenGenerate(User user) {

        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);

            return JWT.create()
                      .withSubject(user.getEmail())
                      .withClaim("id", user.getId())
                      .withClaim("name", user.getName())
                      .withExpiresAt(new Date(System.currentTimeMillis() + 36000000))
                      .withIssuer("Ada Tech")
                      .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new RuntimeException("Unable to generate token", e);
        }
    }

    public String getSubject(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
        
            JWTVerifier verifier = JWT.require(algorithm).withIssuer("Ada Tech").build();

            return verifier.verify(token).getSubject();

        } catch(JWTVerificationException e) {
            throw new RuntimeException("Invalid token", e);
        }
    }
}
