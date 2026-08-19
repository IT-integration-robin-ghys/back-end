package be.ucll.robinghys.integrationproject.user.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import be.ucll.robinghys.integrationproject.general.service.JwtService;
import be.ucll.robinghys.integrationproject.user.dto.AuthenticationResponse;
import be.ucll.robinghys.integrationproject.user.dto.UserInput;
import be.ucll.robinghys.integrationproject.user.model.User;
import be.ucll.robinghys.integrationproject.user.repository.UserRepository;

@Service
@Validated
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
            JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;

    }

    public AuthenticationResponse authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(
                token,
                user.getUsername());
    }

    public User signup(UserInput userDto) {
        if (userRepository.existsByEmail(userDto.email()))
            throw new IllegalArgumentException("Email already in use");

        final var encodedPassword = passwordEncoder.encode(userDto.password());
        User user = new User(
                userDto.username(),
                userDto.email(),
                encodedPassword);

        return userRepository.save(user);
    }
}
