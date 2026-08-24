package be.ucll.robinghys.integrationproject.terrarium.controller;

import be.ucll.robinghys.integrationproject.terrarium.dto.createTerrariumRequestDto;
import be.ucll.robinghys.integrationproject.terrarium.dto.GetTerrariumRequestDto;
import be.ucll.robinghys.integrationproject.terrarium.dto.TerrariumsRequestDto;
import be.ucll.robinghys.integrationproject.terrarium.service.TerrariumService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/link/create")
    public ResponseEntity<String> createTerrariumToUser(
            @RequestBody createTerrariumRequestDto createTerrariumRequestDto) {
        return terrariumService.createTerrariumRequest(createTerrariumRequestDto);
    }

    @PreAuthorize("hasRole('user')")
    @GetMapping("/link/get")
    public List<GetTerrariumRequestDto> getTerrariumRequestsByUserJWT() {
        return terrariumService
                .getTerrariumRequestsByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
