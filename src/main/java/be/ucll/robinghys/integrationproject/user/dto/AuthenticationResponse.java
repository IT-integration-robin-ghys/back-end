package be.ucll.robinghys.integrationproject.user.dto;

public record AuthenticationResponse(
        String token,
        String username) {
}
