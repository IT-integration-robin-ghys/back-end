package be.ucll.robinghys.integrationproject.terrarium.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import be.ucll.robinghys.integrationproject.terrarium.dto.TerrariumsRequestDto;
import be.ucll.robinghys.integrationproject.terrarium.model.Terrarium;
import be.ucll.robinghys.integrationproject.terrarium.repository.TerrariumRepository;

@Service
@Validated
public class TerrariumService {

    private TerrariumRepository terrariumRepository;

    public TerrariumService(TerrariumRepository terrariumRepository) {
        this.terrariumRepository = terrariumRepository;
    }

    public List<TerrariumsRequestDto> getTerrariumsByUserEmail(String email) {
        List<Terrarium> terrariums = terrariumRepository.findAllByUserEmail(email);

        return terrariums.stream()
                .map(terrarium -> new TerrariumsRequestDto(
                        terrarium.getName(), terrarium.getTemperatures(), terrarium.getHumidities()))
                .toList();
    }

}
