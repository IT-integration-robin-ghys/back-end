package be.ucll.robinghys.integrationproject.sensorMeasurement.Dto;

import java.time.LocalDateTime;

public record SensorMeasurementDto(
        Double temperature,
        Double Humidity,
        LocalDateTime timestamp) {

}
