package be.ucll.robinghys.integrationproject.terrarium.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import be.ucll.robinghys.integrationproject.terrarium.model.Terrarium;
import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumId;

public interface TerrariumRepository extends JpaRepository<Terrarium, TerrariumId> {
    List<Terrarium> findAllByUserEmail(String email);
}
