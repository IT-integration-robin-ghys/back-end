package be.ucll.robinghys.integrationproject.terrarium.dto;

import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumRequest.Status;

public record GetTerrariumConnectionDto(
        Status status,
        String APIKey) {

}
