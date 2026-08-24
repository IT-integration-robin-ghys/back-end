package be.ucll.robinghys.integrationproject.terrarium.dto;

import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumRequestId;

public record GetTerrariumRequestDto(
        String terrariumName,
        TerrariumRequestId terrariumRequestId) {
}
