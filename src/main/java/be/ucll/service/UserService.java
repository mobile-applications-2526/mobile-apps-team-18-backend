package be.ucll.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import be.ucll.controller.dto.AuthenticationResponse;
import be.ucll.controller.dto.UserInput;
import be.ucll.controller.dto.UserPongDTO;
import be.ucll.exception.UserException;
import be.ucll.model.User;
import be.ucll.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Authenticates with a given username and password
     *
     * @param username the user's username
     * @param password the user's password (in plaintext)
     * @return an AuthenticationResponse containing a JWT
     */
    public AuthenticationResponse authenticate(String username, String password) {
        final var usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(username, password);
        final var authentication = authenticationManager.authenticate(usernamePasswordAuthentication);
        final var user = ((UserDetailsImpl) authentication.getPrincipal()).user();
        final var token = jwtService.generateToken(user);
        return new AuthenticationResponse(
                "Authentication successful.",
                token,
                user.getUsername(),
                user.getEmail(),
                user.getGeboortedatum(),
                user.getLocatie());
    }

    /**
     * Registers a new user with the given details
     *
     * @param userInput the details to use for registration
     * @return the newly created User
     */
    public AuthenticationResponse signup(UserInput userInput) {
        if (userRepository.existsByUsername(userInput.username())) {
            throw new UserException("Username is already in use.");
        }

        final var hashedPassword = passwordEncoder.encode(userInput.password());
        final var user = new User(
                userInput.username(),
                hashedPassword);

        userRepository.save(user);

        final var token = jwtService.generateToken(user);
        return new AuthenticationResponse(
                "Registration successful.",
                token,
                user.getUsername(),
                user.getEmail(),
                user.getGeboortedatum(),
                user.getLocatie());

    }

    public UserPongDTO ping(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserException("Unauthorized");
        }

        System.out.println(authentication.getName());

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("User not found"));

        return new UserPongDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getGeboortedatum(),
                user.getLocatie());
    }

    public AuthenticationResponse updateUsername(Authentication authentication, String username) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserException("Unauthorized");
        }

        String authUsername = authentication.getName();

        User user = userRepository.findByUsername(authUsername)
                .orElseThrow(() -> new UserException("User " + authUsername + " not found"));

        if (userRepository.existsByUsername(username)) {
            throw new UserException("Username is already in use.");
        }

        user.setUsername(username);
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(
                "Username updated successfully",
                token,
                user.getUsername(),
                user.getEmail(),
                user.getGeboortedatum(),
                user.getLocatie());
    }

    public AuthenticationResponse updateEmail(Authentication authentication, String email) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserException("Unauthorized");
        }

        String authUsername = authentication.getName();

        User user = userRepository.findByUsername(authUsername)
                .orElseThrow(() -> new UserException("User " + authUsername + " not found"));

        if (userRepository.existsByEmail(email)) {
            throw new UserException("Email is already in use.");
        }

        user.setEmail(email);
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(
                "Email updated successfully",
                token,
                user.getUsername(),
                user.getEmail(),
                user.getGeboortedatum(),
                user.getLocatie());
    }

    public AuthenticationResponse updateLocation(Authentication authentication, String location) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserException("Unauthorized");
        }

        String authUsername = authentication.getName();

        User user = userRepository.findByUsername(authUsername)
                .orElseThrow(() -> new UserException("User " + authUsername + " not found"));

        user.setLocatie(location);
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(
                "Location updated successfully",
                token,
                user.getUsername(),
                user.getEmail(),
                user.getGeboortedatum(),
                user.getLocatie());
    }

    public AuthenticationResponse updateBirthday(Authentication authentication, LocalDate birthday) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserException("Unauthorized");
        }

        String authUsername = authentication.getName();

        User user = userRepository.findByUsername(authUsername)
                .orElseThrow(() -> new UserException("User " + authUsername + " not found"));

        user.setGeboortedatum(birthday);
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(
                "Birthday updated successfully",
                token,
                user.getUsername(),
                user.getEmail(),
                user.getGeboortedatum(),
                user.getLocatie());
    }

    public String deleteUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserException("Unauthorized");
        }

        String authUsername = authentication.getName();

        User user = userRepository.findByUsername(authUsername)
                .orElseThrow(() -> new UserException("User " + authUsername + " not found"));

        userRepository.delete(user);
        return "account deleted";
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("User " + username + " not found"));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException("User with id " + id + " not found"));
    }

}
