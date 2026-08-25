package be.ucll.robinghys.integrationproject.sensorMeasurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import be.ucll.robinghys.integrationproject.sensorMeasurement.model.SensorMeasurement;
import be.ucll.robinghys.integrationproject.sensorMeasurement.model.SensorMeasurementId;
import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumId;

public interface SensorMeasurementRepository extends JpaRepository<SensorMeasurement, SensorMeasurementId> {

    List<SensorMeasurement> findAllByIdTerrariumId(TerrariumId terrariumId);

}
