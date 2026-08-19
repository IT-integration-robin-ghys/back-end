package be.ucll.robinghys.integrationproject.general.DBinit;

import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import be.ucll.robinghys.integrationproject.user.model.User;
import be.ucll.robinghys.integrationproject.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Component
@Profile("dev")
public class DBinitializer {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public DBinitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initialize() {
        userRepository.deleteAll();
        String password = passwordEncoder.encode("password");
        User testUser = new User("Robin", "robin@email.com", password);
        userRepository.save(testUser);
    }

}
