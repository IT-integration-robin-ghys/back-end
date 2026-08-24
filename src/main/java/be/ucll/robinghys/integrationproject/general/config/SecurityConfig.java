package be.ucll.robinghys.integrationproject.general.config;

import java.net.URL;
import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableConfigurationProperties(CorsConfig.class)
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(
                                                authorizeRequests -> authorizeRequests
                                                                // Allow users
                                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                                                .requestMatchers("/users/signup", "/users/login",
                                                                                "/users/testJwtAdmin",
                                                                                "/users/testJwtUser")
                                                                .permitAll()
                                                                .anyRequest().authenticated())
                                .cors(cors -> {
                                })
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource(CorsConfig corsConfig) {

                // Allow all normal cors
                CorsConfiguration normalConfig = new CorsConfiguration();

                normalConfig.setAllowedOrigins(
                                corsConfig.allowedOrigins()
                                                .stream()
                                                .map(URL::toString)
                                                .toList());

                normalConfig.setAllowedMethods(List.of(
                                "GET", "POST", "PUT", "DELETE", "OPTIONS"));

                normalConfig.setAllowedHeaders(List.of("*"));

                // Endpoints that CORS doesn't check
                CorsConfiguration openConfig = new CorsConfiguration();

                openConfig.setAllowedOriginPatterns(List.of("*"));

                openConfig.setAllowedMethods(List.of(
                                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

                openConfig.setAllowedHeaders(List.of("*"));

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

                source.registerCorsConfiguration(
                                "/users/testJwtAdmin",
                                openConfig);

                source.registerCorsConfiguration(
                                "/users/testJwtUser",
                                openConfig);

                source.registerCorsConfiguration(
                                "/**",
                                normalConfig);

                return source;
        }
}