package be.ucll.robinghys.integrationproject.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.ucll.robinghys.integrationproject.user.dto.AuthenticationRequest;
import be.ucll.robinghys.integrationproject.user.dto.AuthenticationResponse;
import be.ucll.robinghys.integrationproject.user.dto.UserInput;
import be.ucll.robinghys.integrationproject.user.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        return userService.authenticate(authenticationRequest.email(), authenticationRequest.password());
    }

    @PostMapping("/signup")
    public AuthenticationResponse signup(@Valid @RequestBody UserInput userDto) {
        userService.signup(userDto);
        return userService.authenticate(userDto.email(), userDto.password());
    }

}
