package be.ucll.robinghys.integrationproject.general.config;

import java.net.URL;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "cors")
public record CorsConfig(
        @DefaultValue({
                "http://localhost:3000" }) List<URL> allowedOrigins) {
}
