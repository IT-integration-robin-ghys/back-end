package be.ucll.robinghys.integrationproject.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.ucll.robinghys.integrationproject.user.model.User;
import be.ucll.robinghys.integrationproject.user.model.UserId;

public interface UserRepository extends JpaRepository<User, UserId> {

}
