package be.ucll.robinghys.integrationproject.sensorMeasurement.model;

import java.time.LocalDateTime;

import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;

public class SensorMeasurementId {

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "terrarium_id"))
    private TerrariumId terrariumId;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public SensorMeasurementId(TerrariumId terrariumId, LocalDateTime timestamp) {
        setTerrariumId(terrariumId);
        setTimestamp(timestamp);
    }

    protected SensorMeasurementId() {
    }

    public TerrariumId getTerrariumId() {
        return terrariumId;
    }

    public void setTerrariumId(TerrariumId terrariumId) {
        this.terrariumId = terrariumId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
