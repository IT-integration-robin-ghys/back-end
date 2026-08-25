package be.ucll.robinghys.integrationproject.general.DBinit;

import be.ucll.robinghys.integrationproject.sensorMeasurement.model.SensorMeasurement;
import be.ucll.robinghys.integrationproject.sensorMeasurement.repository.SensorMeasurementRepository;
import be.ucll.robinghys.integrationproject.terrarium.repository.TerrariumRequestRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.context.annotation.Profile;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import be.ucll.robinghys.integrationproject.terrarium.model.Terrarium;
import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumRequest;
import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumRequest.Status;
import be.ucll.robinghys.integrationproject.terrarium.repository.TerrariumRepository;
import be.ucll.robinghys.integrationproject.user.model.Role;
import be.ucll.robinghys.integrationproject.user.model.User;
import be.ucll.robinghys.integrationproject.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Component
@Profile("dev")
public class DBinitializer {

        private final SensorMeasurementRepository sensorMeasurementRepository;
        private TerrariumRequestRepository terrariumRequestRepository;
        private UserRepository userRepository;
        private TerrariumRepository terrariumRepository;

        public DBinitializer(
                        UserRepository userRepository,
                        TerrariumRepository terrariumRepository,
                        TerrariumRequestRepository terrariumRequestRepository,
                        SensorMeasurementRepository sensorMeasurementRepository) {

                this.userRepository = userRepository;
                this.terrariumRepository = terrariumRepository;
                this.terrariumRequestRepository = terrariumRequestRepository;
                this.sensorMeasurementRepository = sensorMeasurementRepository;
        }

        @PostConstruct
        public void initialize() {
                terrariumRequestRepository.deleteAll();
                terrariumRepository.deleteAll();
                userRepository.deleteAll();
                sensorMeasurementRepository.deleteAll();

                String password = BCrypt.withDefaults()
                                .hashToString(12, "password".toCharArray());
                User testUser = new User("Robin", "robin@email.com", password);
                User testAdmin = new User("Admin", "admin@email.com", password);
                testAdmin.setRole(Role.admin);
                userRepository.save(testUser);
                userRepository.save(testAdmin);

                Terrarium terrarium = new Terrarium("terrarium 1", UUID.randomUUID());
                terrarium.addHumidity(1.0);
                terrarium.addHumidity(10.0);
                terrarium.addHumidity(10.9);

                terrarium.addTemperature(67.0);
                terrarium.addTemperature(67.0);
                terrarium.addTemperature(67.0);
                terrarium.addTemperature(67.0);

                terrarium.setUser(testUser);

                terrariumRepository.save(terrarium);

                TerrariumRequest terrariumRequestPending = new TerrariumRequest(testUser.getId(),
                                terrarium.getId());
                TerrariumRequest terrariumRequestAccepted = new TerrariumRequest(testUser.getId(),
                                terrarium.getId());

                terrariumRequestAccepted.setStatus(Status.ACCEPTED);

                terrariumRequestRepository.save(terrariumRequestPending);
                terrariumRequestRepository.save(terrariumRequestAccepted);

                SensorMeasurement sensorMeasurement1 = new SensorMeasurement(terrarium.getId(), LocalDateTime.now(),
                                42.0, 60.0);

                sensorMeasurementRepository.save(sensorMeasurement1);
        }

}
