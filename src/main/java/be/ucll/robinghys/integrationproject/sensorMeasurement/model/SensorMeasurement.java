package be.ucll.robinghys.integrationproject.sensorMeasurement.model;

import java.time.LocalDateTime;

import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "sensor_measurement", schema = "public")
public class SensorMeasurement {

    @EmbeddedId
    private SensorMeasurementId id;

    private Double temperature;

    private Double humidity;

    public SensorMeasurement(TerrariumId terrariumId, LocalDateTime timestamp, Double temperature, Double humidity) {
        this.id = new SensorMeasurementId(terrariumId, timestamp);
        setTemperature(temperature);
        setHumidity(humidity);
    }

    protected SensorMeasurement() {
    }

    public SensorMeasurementId getId() {
        return id;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }
}
