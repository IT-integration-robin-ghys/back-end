package be.ucll.robinghys.integrationproject.terrarium.controller;

import be.ucll.robinghys.integrationproject.terrarium.dto.createTerrariumRequestDto;
import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumId;
import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumRequestId;
import be.ucll.robinghys.integrationproject.sensorMeasurement.Dto.PostSensorMeasurementDto;
import be.ucll.robinghys.integrationproject.terrarium.dto.GetTerrariumConnectionDto;
import be.ucll.robinghys.integrationproject.terrarium.dto.GetTerrariumRequestDto;
import be.ucll.robinghys.integrationproject.terrarium.dto.TerrariumsRequestDto;
import be.ucll.robinghys.integrationproject.terrarium.service.TerrariumService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/terrariums")
public class TerrariumController {

    private final TerrariumService terrariumService;

    public TerrariumController(TerrariumService terrariumService) {
        this.terrariumService = terrariumService;
    }

    @PreAuthorize("hasRole('user')")
    @GetMapping
    public List<TerrariumsRequestDto> getTerrariumsByUserJWT() {
        return terrariumService
                .getTerrariumsByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PreAuthorize("hasRole('user')")
    @GetMapping("/{terrariumId}")
    public TerrariumsRequestDto getTerrariumByUserJWTAndTerrariumId(@PathVariable UUID terrariumId) {
        return terrariumService
                .getTerrariumByTerrariumId(SecurityContextHolder.getContext().getAuthentication().getName(),
                        new TerrariumId(terrariumId));
    }

    @PostMapping("/link")
    public ResponseEntity<String> createTerrariumToUser(
            @RequestBody createTerrariumRequestDto createTerrariumRequestDto) {
        return terrariumService.createTerrariumRequest(createTerrariumRequestDto);
    }

    @PreAuthorize("hasRole('user')")
    @GetMapping("/link/me")
    public List<GetTerrariumRequestDto> getTerrariumRequestsByUserJWT() {
        return terrariumService
                .getTerrariumRequestsByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PreAuthorize("hasRole('user')")
    @PostMapping("/link/{terrariumRequestId}/accept")
    public ResponseEntity<Map<String, String>> acceptTerrariumRequestByTerrariumRequestId(
            @PathVariable UUID terrariumRequestId) {
        return terrariumService.acceptTerrariumRequestByTerrariumRequestIdAndUserEmail(
                new TerrariumRequestId(terrariumRequestId),
                SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PreAuthorize("hasRole('user')")
    @PostMapping("/link/{terrariumRequestId}/deny")
    public ResponseEntity<Map<String, String>> denyTerrariumRequestByTerrariumRequestId(
            @PathVariable UUID terrariumRequestId) {
        return terrariumService.denyTerrariumRequestByTerrariumRequestIdAndUserEmail(
                new TerrariumRequestId(terrariumRequestId),
                SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @GetMapping("/link/{terrariumId}")
    public GetTerrariumConnectionDto getTerrariumRequestESP32(@PathVariable UUID terrariumId) {
        return terrariumService.getTerrariumConnection(new TerrariumId(terrariumId));
    }

    @PostMapping("/data/{terrariumId}")
    public ResponseEntity<String> postSensorMeasurementsEsp32(
            @PathVariable UUID terrariumId,
            @RequestBody PostSensorMeasurementDto sensorMeasurements,
            @RequestHeader("X-API-Key") String apikey) {

        return terrariumService.SaveSensorMeasurement(sensorMeasurements, apikey, new TerrariumId(terrariumId));
    }

    @GetMapping("/settings/esp32/{terrariumId}")
    public ResponseEntity<String> getSettingsEsp32(@PathVariable UUID terrariumId,
            @RequestHeader("X-API-Key") String apikey) {
        return terrariumService.getSettingsEsp32(new TerrariumId(terrariumId), apikey);
    }

    @PreAuthorize("hasRole('user')")
    @GetMapping("/settings/web/{terrariumId}")
    public ResponseEntity<JsonNode> getSettingsWeb(@PathVariable UUID terrariumId) {
        return terrariumService.getSettingsWeb(new TerrariumId(terrariumId),
                SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PostMapping("/settings/esp32/{terrariumId}")
    public ResponseEntity<String> postSettingsEsp32(@RequestBody String settings, @PathVariable UUID terrariumId,
            @RequestHeader("X-API-Key") String apikey) {

        return terrariumService.SaveTerrariumSettingsEsp32(settings, apikey, new TerrariumId(terrariumId));
    }

    @PreAuthorize("hasRole('user')")
    @PostMapping("/settings/web/{terrariumId}")
    public ResponseEntity<Map<String, String>> postSettingsWeb(@RequestBody String settings, @PathVariable UUID terrariumId) {

        return terrariumService.SaveTerrariumSettingsWeb(settings,
                SecurityContextHolder.getContext().getAuthentication().getName(), new TerrariumId(terrariumId));
    }

}
