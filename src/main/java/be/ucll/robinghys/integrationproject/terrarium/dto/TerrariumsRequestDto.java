package be.ucll.robinghys.integrationproject.terrarium.dto;

import java.util.List;

import be.ucll.robinghys.integrationproject.sensorMeasurement.Dto.SensorMeasurementDto;

public record TerrariumsRequestDto(
                String name,
                List<SensorMeasurementDto> sensorMeasurements) {

}
