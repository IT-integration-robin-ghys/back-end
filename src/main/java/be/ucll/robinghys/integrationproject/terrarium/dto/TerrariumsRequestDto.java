package be.ucll.robinghys.integrationproject.terrarium.dto;

import java.util.List;
import java.util.UUID;

import be.ucll.robinghys.integrationproject.sensorMeasurement.Dto.SensorMeasurementDto;

public record TerrariumsRequestDto(
        UUID id,
        String name,
        List<SensorMeasurementDto> sensorMeasurements) {
}
