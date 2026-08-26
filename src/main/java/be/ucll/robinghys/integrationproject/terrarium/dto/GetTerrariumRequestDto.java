package be.ucll.robinghys.integrationproject.terrarium.dto;

import java.util.UUID;

public record GetTerrariumRequestDto(
                String terrariumName,
                UUID id) {
}
