package be.ucll.robinghys.integrationproject.terrarium.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumRequest;
import be.ucll.robinghys.integrationproject.terrarium.model.TerrariumRequestId;
import be.ucll.robinghys.integrationproject.user.model.UserId;

public interface TerrariumRequestRepository extends JpaRepository<TerrariumRequest, TerrariumRequestId> {

    List<TerrariumRequest> findAllByUserId(UserId id);

}
