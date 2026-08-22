package be.ucll.robinghys.integrationproject.general.DBinit;

import org.springframework.context.annotation.Profile;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import be.ucll.robinghys.integrationproject.terrarium.model.Terrarium;
import be.ucll.robinghys.integrationproject.terrarium.repository.TerrariumRepository;
import be.ucll.robinghys.integrationproject.user.model.Role;
import be.ucll.robinghys.integrationproject.user.model.User;
import be.ucll.robinghys.integrationproject.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Component
@Profile("dev")
public class DBinitializer {

    private UserRepository userRepository;
    private TerrariumRepository terrariumRepository;

    public DBinitializer(
            UserRepository userRepository,
            TerrariumRepository terrariumRepository) {

        this.userRepository = userRepository;
        this.terrariumRepository = terrariumRepository;
    }

    @PostConstruct
    public void initialize() {
        terrariumRepository.deleteAll();
        userRepository.deleteAll();

        String password = BCrypt.withDefaults()
                .hashToString(12, "password".toCharArray());
        User testUser = new User("Robin", "robin@email.com", password);
        User testAdmin = new User("Admin", "admin@email.com", password);
        testAdmin.setRole(Role.admin);
        userRepository.save(testUser);
        userRepository.save(testAdmin);

        Terrarium terrarium = new Terrarium("terrarium 1");
        terrarium.addHumidities(1.0);
        terrarium.addHumidities(10.0);
        terrarium.addHumidities(10.9);

        terrarium.addTemperature(67.0);
        terrarium.addTemperature(67.0);
        terrarium.addTemperature(67.0);
        terrarium.addTemperature(67.0);

        terrarium.setUser(testUser);

        terrariumRepository.save(terrarium);
    }

}
