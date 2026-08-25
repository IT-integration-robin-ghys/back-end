package be.ucll.robinghys.integrationproject.sensorMeasurement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.ucll.robinghys.integrationproject.sensorMeasurement.model.SensorMeasurement;
import be.ucll.robinghys.integrationproject.sensorMeasurement.model.SensorMeasurementId;

public interface SensorMeasurementRepository extends JpaRepository<SensorMeasurement, SensorMeasurementId> {

}
