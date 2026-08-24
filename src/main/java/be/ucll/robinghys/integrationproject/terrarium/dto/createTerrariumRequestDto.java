package be.ucll.robinghys.integrationproject.terrarium.dto;

import java.util.UUID;

public record createTerrariumRequestDto(
        String email,
        UUID terrariumId,
        String terrariumName) {

}
