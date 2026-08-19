package be.ucll.robinghys.integrationproject.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserInput(
        @NotBlank(message = "Username cannot be empty.") String username,
        @Email(message = "Invalid email format") @NotBlank(message = "Email cannot be empty.") String email,
        @NotBlank(message = "Password cannot be empty.") @Size(min = 8, message = "Password can't be smaller than 8 characters.") String password) {
}
