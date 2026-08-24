package be.ucll.robinghys.integrationproject.user.service;

import org.springframework.stereotype.Service;

@Service
public class AuthValidationService {

    public void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("Email cannot be empty.");
        }

        if (email.contains(" ")) {
            throw new RuntimeException("Email cannot contain spaces.");
        }

        String emailRegex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";

        if (!email.matches(emailRegex)) {
            throw new RuntimeException("Invalid Email.");
        }
    }

    public void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new RuntimeException("Password cannot be empty.");
        }

        if (password.contains(" ")) {
            throw new RuntimeException("Password cannot contain spaces.");
        }

        if (password.length() < 8) {
            throw new RuntimeException("Password has to have atleast 8 characters.");
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new RuntimeException("Password has to contain at least 1 capital letter.");
        }

        if (!password.matches(".*\\d.*")) {
            throw new RuntimeException("Password has to contain at least 1 number.");
        }

        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw new RuntimeException("Password has to contain at least 1 special character.");
        }
    }

    public void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("Username cannot be empty");
        }

        if (username.length() < 3) {
            throw new RuntimeException("Username has to have at least 3 characters.");
        }
    }
}