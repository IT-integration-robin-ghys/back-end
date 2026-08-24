package be.ucll.robinghys.integrationproject.terrarium.controller;

import be.ucll.robinghys.integrationproject.terrarium.dto.createTerrariumRequestDto;
import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumId;
import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumRequestId;
import be.ucll.robinghys.integrationproject.terrarium.dto.GetTerrariumConnectionDto;
import be.ucll.robinghys.integrationproject.terrarium.dto.GetTerrariumRequestDto;
import be.ucll.robinghys.integrationproject.terrarium.dto.TerrariumsRequestDto;
import be.ucll.robinghys.integrationproject.terrarium.service.TerrariumService;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("/link")
    public ResponseEntity<String> createTerrariumToUser(
            @RequestBody createTerrariumRequestDto createTerrariumRequestDto) {
        return terrariumService.createTerrariumRequest(createTerrariumRequestDto);
    }

    @PreAuthorize("hasRole('user')")
    @GetMapping("/link")
    public List<GetTerrariumRequestDto> getTerrariumRequestsByUserJWT() {
        return terrariumService
                .getTerrariumRequestsByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PostMapping("/link/{terrariumRequestId}/accept")
    public ResponseEntity<String> acceptTerrariumRequestByTerrariumRequestId(@PathVariable UUID terrariumRequestId) {
        return terrariumService.acceptTerrariumRequestByTerrariumRequestIdAndUserEmail(
                new TerrariumRequestId(terrariumRequestId),
                SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PostMapping("/link/{terrariumRequestId}/deny")
    public ResponseEntity<String> denyTerrariumRequestByTerrariumRequestId(@PathVariable UUID terrariumRequestId) {
        return terrariumService.denyTerrariumRequestByTerrariumRequestIdAndUserEmail(
                new TerrariumRequestId(terrariumRequestId),
                SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @GetMapping("/link/{terrariumId}")
    public GetTerrariumConnectionDto getTerrariumRequestESP32(@PathVariable TerrariumId terrariumId) {
        return terrariumService.getTerrariumConnection(terrariumId);
    }

}
