package be.ucll.robinghys.integrationproject.terrarium.service;

import be.ucll.robinghys.integrationproject.terrarium.repository.TerrariumRequestRepository;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import be.ucll.robinghys.integrationproject.terrarium.dto.createTerrariumRequestDto;
import be.ucll.robinghys.integrationproject.terrarium.dto.TerrariumsRequestDto;
import be.ucll.robinghys.integrationproject.terrarium.model.Terrarium;
import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumRequest;
import be.ucll.robinghys.integrationproject.terrarium.repository.TerrariumRepository;

@Service
@Validated
public class TerrariumService {

    private final TerrariumRequestRepository terrariumRequestRepository;
    private TerrariumRepository terrariumRepository;

    public TerrariumService(TerrariumRepository terrariumRepository,
            TerrariumRequestRepository terrariumRequestRepository) {
        this.terrariumRepository = terrariumRepository;
        this.terrariumRequestRepository = terrariumRequestRepository;
    }

    public List<TerrariumsRequestDto> getTerrariumsByUserEmail(String email) {
        List<Terrarium> terrariums = terrariumRepository.findAllByUserEmail(email);

        return terrariums.stream()
                .map(terrarium -> new TerrariumsRequestDto(
                        terrarium.getName(), terrarium.getTemperatures(), terrarium.getHumidities()))
                .toList();
    }

    public ResponseEntity<String> createTerrariumRequest(
            createTerrariumRequestDto createTerrariumRequestDto) {
        TerrariumRequest terrariumRequest = new TerrariumRequest(createTerrariumRequestDto.id(),
                createTerrariumRequestDto.userId(),
                createTerrariumRequestDto.terrariumId());
        terrariumRequestRepository.save(terrariumRequest);

        return ResponseEntity.ok("success");
    }
}
