package be.ucll.robinghys.integrationproject.user.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import at.favre.lib.crypto.bcrypt.BCrypt;
import be.ucll.robinghys.integrationproject.general.config.JwtUtil;
import be.ucll.robinghys.integrationproject.user.dto.AuthenticationResponse;
import be.ucll.robinghys.integrationproject.user.model.User;
import be.ucll.robinghys.integrationproject.user.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthValidationService authValidationService;
    private final JwtUtil jwtUtil;

    @Value("${jwt.token.issuer}")
    private String issuer;

    public AuthService(
            UserService userService,
            UserRepository userRepository,
            AuthValidationService authValidationService,
            JwtUtil jwtUtil) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authValidationService = authValidationService;
        this.jwtUtil = jwtUtil;
    }

    public AuthenticationResponse signup(String username, String email, String password) {

        authValidationService.validateUsername(username);
        authValidationService.validateEmail(email);
        authValidationService.validatePassword(password);

        if (userService.userExistsByEmail(email)) {
            throw new RuntimeException("Something went wrong logging in this user.");
        }

        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        User user = new User(username, email, hashedPassword);

        try {
            userRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Something went wrong logging in this user.");
        }

        return loginUser(email, password);
    }

    public AuthenticationResponse loginUser(String email, String password) {

        authValidationService.validateEmail(email);
        User user = userService.findUserByEmail(email);

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

        if (!result.verified) {
            throw new RuntimeException("Something went wrong logging in this user.");
        }

        try {
            Algorithm algorithm = Algorithm.HMAC512(jwtUtil.getSecret());

            Date expiresAt = new Date(System.currentTimeMillis() + jwtUtil.getlifetime());

            String token = JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getEmail())
                    .withExpiresAt(expiresAt)
                    .sign(algorithm);

            return new AuthenticationResponse(token, user.getUsername());

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Something went wrong logging in this user.");
        }
    }

}
