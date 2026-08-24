package be.ucll.robinghys.integrationproject.terrarium.controller;

import be.ucll.robinghys.integrationproject.terrarium.dto.TerrariumsRequestDto;
import be.ucll.robinghys.integrationproject.terrarium.service.TerrariumService;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
