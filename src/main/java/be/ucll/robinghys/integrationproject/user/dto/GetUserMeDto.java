package be.ucll.robinghys.integrationproject.user.dto;

import be.ucll.robinghys.integrationproject.user.model.Role;

public record GetUserMeDto(
        String username,
        String email,
        Role role) {

}
