package be.ucll.robinghys.integrationproject.terrarium.dto;

import java.util.List;

import be.ucll.robinghys.integrationproject.sensorMeasurement.Dto.SensorMeasurementDto;
import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumId;

public record TerrariumsRequestDto(
        TerrariumId terrariumId,
        String name,
        List<SensorMeasurementDto> sensorMeasurements) {
}
