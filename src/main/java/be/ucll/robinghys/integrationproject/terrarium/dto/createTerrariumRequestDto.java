package be.ucll.robinghys.integrationproject.terrarium.dto;

import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumId;

public record createTerrariumRequestDto(
        String email,
        TerrariumId terrariumId) {

}
