package be.ucll.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import be.ucll.controller.dto.AuthenticationRequest;
import be.ucll.controller.dto.AuthenticationResponse;
import be.ucll.controller.dto.UserInput;
import be.ucll.controller.dto.UserPongDTO;
import be.ucll.model.User;
import be.ucll.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        return userService.authenticate(authenticationRequest.username(), authenticationRequest.password());
    }

    @PostMapping("/signup")
    public AuthenticationResponse signup(@Valid @RequestBody UserInput userInput) {
        return userService.signup(userInput);
    }

    @GetMapping("/ping")
    public UserPongDTO ping(Authentication authentication) {
        return userService.ping(authentication);
    }

    @PutMapping("/username/{username}")
    public AuthenticationResponse putUsername(Authentication authentication, @PathVariable String username) {
        return userService.updateUsername(authentication, username);
    }

    @PutMapping("/email/{email}")
    public AuthenticationResponse putEmail(Authentication authentication, @PathVariable String email) {
        return userService.updateEmail(authentication, email);
    }

    @PutMapping("/location/{location}")
    public AuthenticationResponse putLocation(Authentication authentication, @PathVariable String location) {
        return userService.updateLocation(authentication, location);
    }

    @PutMapping("/birthday/{birthday}")
    public AuthenticationResponse putBirthday(Authentication authentication, @PathVariable LocalDate birthday) {
        return userService.updateBirthday(authentication, birthday);
    }

    @DeleteMapping()
    public String deleteUser(Authentication authentication) {
        return userService.deleteUser(authentication);
    }
}
