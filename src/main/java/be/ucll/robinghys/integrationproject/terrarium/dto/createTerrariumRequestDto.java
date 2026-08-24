package be.ucll.robinghys.integrationproject.terrarium.dto;

import java.util.UUID;

import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumId;
import be.ucll.robinghys.integrationproject.user.model.UserId;

public record createTerrariumRequestDto(
                UUID id,
                UserId userId,
                TerrariumId terrariumId) {

}
