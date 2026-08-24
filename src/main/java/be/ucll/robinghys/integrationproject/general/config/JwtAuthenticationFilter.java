package be.ucll.robinghys.integrationproject.general.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import be.ucll.robinghys.integrationproject.user.model.Role;
import be.ucll.robinghys.integrationproject.user.model.User;
import be.ucll.robinghys.integrationproject.user.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                if (jwtUtil.validateToken(token)) {
                    String email = jwtUtil.extractEmail(token);
                    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        User user = userService.findUserByEmail(email);
                        if (user != null) {
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                    email, null, buildAuthorities(user.getRole()));
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    }
                }
            } catch (RuntimeException ignored) {
                // Ignore so the program doesn't stop working on error
            }
        }

        filterChain.doFilter(request, response);
    }

    private List<SimpleGrantedAuthority> buildAuthorities(Role role) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role candidate : Role.values()) {
            if (role.getLevel() >= candidate.getLevel()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + candidate.name()));
            }
        }

        return authorities;
    }
}