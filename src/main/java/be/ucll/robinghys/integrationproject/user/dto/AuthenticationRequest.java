package be.ucll.robinghys.integrationproject.user.dto;

public record AuthenticationRequest(
        String email,
        String password) {
}
