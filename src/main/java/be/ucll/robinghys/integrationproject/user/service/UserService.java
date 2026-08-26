package be.ucll.robinghys.integrationproject.user.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import be.ucll.robinghys.integrationproject.user.dto.GetUserMeDto;
import be.ucll.robinghys.integrationproject.user.model.User;
import be.ucll.robinghys.integrationproject.user.model.UserId;
import be.ucll.robinghys.integrationproject.user.repository.UserRepository;

@Service
@Validated
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public boolean userExistsByEmail(String email) {
        return findUserByEmail(email) != null;
    }

    public User findUserByUserId(UserId id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public GetUserMeDto getUserMe(String email) {
        User user = findUserByEmail(email);
        return new GetUserMeDto(user.getUsername(), user.getEmail(), user.getRole());
    }
}
