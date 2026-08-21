package be.ucll.robinghys.integrationproject.general.DBinit;

import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import be.ucll.robinghys.integrationproject.terrarium.model.Terrarium;
import be.ucll.robinghys.integrationproject.terrarium.repository.TerrariumRepository;
import be.ucll.robinghys.integrationproject.user.model.User;
import be.ucll.robinghys.integrationproject.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Component
@Profile("dev")
public class DBinitializer {

    private UserRepository userRepository;
    private TerrariumRepository terrariumRepository;
    private PasswordEncoder passwordEncoder;

    public DBinitializer(UserRepository userRepository, TerrariumRepository terrariumRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.terrariumRepository = terrariumRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initialize() {
        userRepository.deleteAll();
        terrariumRepository.deleteAll();

        String password = passwordEncoder.encode("password");
        User testUser = new User("Robin", "robin@email.com", password);
        userRepository.save(testUser);

        Terrarium terrarium = new Terrarium("terrarium 1");
        terrarium.addHumidities(1.0);
        terrarium.addHumidities(10.0);

        terrarium.addTemperature(67.0);
        terrarium.addTemperature(67.0);
        terrarium.addTemperature(67.0);
        terrarium.addTemperature(67.0);

        terrarium.setUser(testUser);

        terrariumRepository.save(terrarium);
    }

}
