package be.ucll.robinghys.integrationproject.terrarium.dto;

import java.util.List;

public record TerrariumsRequestDto(
        String name,
        List<Double> temperatures,
        List<Double> humidities
) {

}
