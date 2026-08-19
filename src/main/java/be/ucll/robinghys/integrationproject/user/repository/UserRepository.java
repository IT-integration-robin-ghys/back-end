package be.ucll.robinghys.integrationproject.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import be.ucll.robinghys.integrationproject.user.model.User;
import be.ucll.robinghys.integrationproject.user.model.UserId;

public interface UserRepository extends JpaRepository<User, UserId> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

}
