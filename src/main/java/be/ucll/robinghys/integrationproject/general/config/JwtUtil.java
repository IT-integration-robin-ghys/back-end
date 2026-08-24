package be.ucll.robinghys.integrationproject.general.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secret;

    @Value("${jwt.token.lifetime}")
    private Long lifetime;

    @Value("${jwt.token.issuer}")
    private String issuer;

    public String extractEmail(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            DecodedJWT jwt = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token);
            return jwt.getSubject();
        } catch (TokenExpiredException e) {
            throw new RuntimeException("Token is expired.");
        } catch (JWTVerificationException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token);
            return true;
        } catch (TokenExpiredException e) {
            throw new RuntimeException("Token is expired.");
        } catch (JWTVerificationException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public String getSecret() {
        return secret;
    }

    public Long getExpiration() {
        return lifetime;
    }

    public Long getlifetime() {
        return lifetime;
    }

    public String getIssuer() {
        return issuer;
    }
}