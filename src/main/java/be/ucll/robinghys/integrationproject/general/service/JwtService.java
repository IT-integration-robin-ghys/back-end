package be.ucll.robinghys.integrationproject.general.service;

import java.time.Instant;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import be.ucll.robinghys.integrationproject.general.config.JwtConfig;
import be.ucll.robinghys.integrationproject.user.model.User;

@Service
public class JwtService {
    private final JwtConfig jwtConfig;
    private final JwtEncoder jwtEncoder;

    public JwtService(JwtConfig jwtConfig, JwtEncoder jwtEncoder) {
        this.jwtConfig = jwtConfig;
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(String email) {
        final var now = Instant.now();
        final var expiresAt = now.plus(jwtConfig.token().lifetime());
        final var header = JwsHeader.with(MacAlgorithm.HS256).build();
        final var claims = JwtClaimsSet.builder()
                .issuer(jwtConfig.token().issuer())
                .issuedAt(now)
                .expiresAt(expiresAt)
                .subject(email)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    public String generateToken(User user) {
        return generateToken(user.getEmail());
    }
}
