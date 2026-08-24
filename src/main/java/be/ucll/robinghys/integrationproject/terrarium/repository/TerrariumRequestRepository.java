package be.ucll.robinghys.integrationproject.terrarium.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumRequest;
import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumRequestId;

public interface TerrariumRequestRepository extends JpaRepository<TerrariumRequest, TerrariumRequestId> {

}
