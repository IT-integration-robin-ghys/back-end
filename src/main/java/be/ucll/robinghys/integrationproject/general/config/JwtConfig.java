package be.ucll.robinghys.integrationproject.general.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "jwt")
public record JwtConfig(String secretKey,
                @DefaultValue Token token) {
        public record Token(@DefaultValue("ITIntegration-Robin-Ghys") String issuer,
                        @DefaultValue("7d") Duration lifetime) {
        }
}
