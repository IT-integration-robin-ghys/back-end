package be.ucll.robinghys.integrationproject.terrarium.service;

import be.ucll.robinghys.integrationproject.sensorMeasurement.Dto.SensorMeasurementDto;
import be.ucll.robinghys.integrationproject.sensorMeasurement.repository.SensorMeasurementRepository;
import be.ucll.robinghys.integrationproject.terrarium.repository.TerrariumRequestRepository;
import be.ucll.robinghys.integrationproject.user.model.User;
import be.ucll.robinghys.integrationproject.user.service.UserService;
import java.util.HexFormat;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import be.ucll.robinghys.integrationproject.terrarium.dto.createTerrariumRequestDto;
import be.ucll.robinghys.integrationproject.terrarium.dto.GetTerrariumConnectionDto;
import be.ucll.robinghys.integrationproject.terrarium.dto.GetTerrariumRequestDto;
import be.ucll.robinghys.integrationproject.terrarium.dto.TerrariumsRequestDto;
import be.ucll.robinghys.integrationproject.terrarium.model.Terrarium;
import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumId;
import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumRequest;
import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumRequestId;
import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumRequest.Status;
import be.ucll.robinghys.integrationproject.terrarium.repository.TerrariumRepository;

@Service
@Validated
public class TerrariumService {

    private final SensorMeasurementRepository sensorMeasurementRepository;
    private final UserService userService;
    private final TerrariumRequestRepository terrariumRequestRepository;
    private TerrariumRepository terrariumRepository;

    public TerrariumService(TerrariumRepository terrariumRepository,
            TerrariumRequestRepository terrariumRequestRepository, UserService userService,
            SensorMeasurementRepository sensorMeasurementRepository) {
        this.terrariumRepository = terrariumRepository;
        this.terrariumRequestRepository = terrariumRequestRepository;
        this.userService = userService;
        this.sensorMeasurementRepository = sensorMeasurementRepository;
    }

    public List<TerrariumsRequestDto> getTerrariumsByUserEmail(String email) {
        List<Terrarium> terrariums = terrariumRepository.findAllByUserEmail(email);

        return terrariums.stream()
                .map(terrarium -> {
                    List<SensorMeasurementDto> measurements = sensorMeasurementRepository
                            .findAllByIdTerrariumId(terrarium.getId())
                            .stream()
                            .map(measurement -> new SensorMeasurementDto(
                                    measurement.getTemperature(),
                                    measurement.getHumidity(),
                                    measurement.getId().getTimestamp()))
                            .toList();

                    return new TerrariumsRequestDto(
                            terrarium.getName(),
                            measurements);
                })
                .toList();
    }

    public ResponseEntity<String> createTerrariumRequest(createTerrariumRequestDto createTerrariumRequestDto) {
        User user = userService.findUserByEmail(createTerrariumRequestDto.email());

        Terrarium terrarium = new Terrarium(createTerrariumRequestDto.terrariumName(),
                createTerrariumRequestDto.terrariumId());

        terrariumRepository.save(terrarium);

        TerrariumRequest terrariumRequest = new TerrariumRequest(user.getId(), terrarium.getId());

        terrariumRequestRepository.save(terrariumRequest);

        return ResponseEntity.ok("success");
    }

    public List<GetTerrariumRequestDto> getTerrariumRequestsByEmail(String email) {
        User user = userService.findUserByEmail(email);
        List<TerrariumRequest> terrariumRequests = terrariumRequestRepository.findAllByUserId(user.getId());

        return terrariumRequests.stream()
                .map(request -> {
                    Terrarium terrarium = terrariumRepository
                            .findById(request.getTerrariumId())
                            .orElseThrow();

                    return new GetTerrariumRequestDto(
                            terrarium.getName(),
                            request.getId());
                })
                .toList();
    }

    public ResponseEntity<String> acceptTerrariumRequestByTerrariumRequestIdAndUserEmail(TerrariumRequestId id,
            String email) {
        User user = userService.findUserByEmail(email);
        TerrariumRequest terrariumRequest = terrariumRequestRepository.findById(id)
                .orElseThrow();

        if (!user.equals(userService.findUserByUserId(terrariumRequest.getUserId()))) {
            throw new RuntimeException("This terrarium is not connected to the given user.");
        }

        terrariumRequest.setStatus(Status.ACCEPTED);

        return ResponseEntity.ok("Successfully accepted.");
    }

    public ResponseEntity<String> denyTerrariumRequestByTerrariumRequestIdAndUserEmail(TerrariumRequestId id,
            String email) {
        User user = userService.findUserByEmail(email);
        TerrariumRequest terrariumRequest = terrariumRequestRepository.findById(id)
                .orElseThrow();

        if (!user.equals(userService.findUserByUserId(terrariumRequest.getUserId()))) {
            throw new RuntimeException("This terrarium is not connected to the given user.");
        }

        terrariumRequest.setStatus(Status.REJECTED);

        return ResponseEntity.ok("Successfully denied.");
    }

    private String generateApiKey() {
        byte[] key = KeyGenerators.secureRandom(32).generateKey();
        return HexFormat.of().formatHex(key);
    }

    public GetTerrariumConnectionDto getTerrariumConnection(TerrariumId terrariumId) {
        TerrariumRequest terrariumRequest = terrariumRequestRepository.findByTerrariumId(terrariumId);
        String apikey = null;

        if (terrariumRequest.getStatus().equals(Status.ACCEPTED)) {
            Terrarium terrarium = terrariumRepository.findById(terrariumId).orElseThrow();
            apikey = terrarium.getApiKey();

            if (apikey == null) {
                apikey = generateApiKey();

                terrarium.setApiKey(apikey);
                terrariumRepository.save(terrarium);
            }
        }
        return new GetTerrariumConnectionDto(terrariumRequest.getStatus(), apikey);
    }
}
