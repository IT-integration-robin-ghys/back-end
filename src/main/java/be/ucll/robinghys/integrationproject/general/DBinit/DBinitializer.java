package be.ucll.robinghys.integrationproject.general.DBinit;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import be.ucll.robinghys.integrationproject.user.model.User;
import be.ucll.robinghys.integrationproject.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Component
@Profile("dev")
public class DBinitializer {

    private UserRepository userRepository;

    public DBinitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void initialize() {
        userRepository.deleteAll();

        User testUser = new User("Robin", "robin@email.com", "password");
        userRepository.save(testUser);
    }

}
