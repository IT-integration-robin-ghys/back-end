package be.ucll.robinghys.integrationproject.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import be.ucll.robinghys.integrationproject.user.dto.AuthenticationRequest;
import be.ucll.robinghys.integrationproject.user.dto.AuthenticationResponse;
import be.ucll.robinghys.integrationproject.user.dto.UserInput;
import be.ucll.robinghys.integrationproject.user.service.AuthService;
import be.ucll.robinghys.integrationproject.user.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @SuppressWarnings("unused") //temp because i hate the yellow file
    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        return authService.loginUser(authenticationRequest.email(), authenticationRequest.password());
    }

    @PostMapping("/signup")
    public AuthenticationResponse signup(@Valid @RequestBody UserInput userDto) {
        return authService.signup(userDto.username(), userDto.email(), userDto.password());
    }

    @PreAuthorize("hasRole('user')")
    @GetMapping("/testJwtUser")
    public String testJwtUser() {
        return "success";
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/testJwtAdmin")
    public String testJwtAdmin() {
        return "success";
    }

}
