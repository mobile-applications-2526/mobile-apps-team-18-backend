package be.ucll.controller.dto;

import java.time.LocalDate;

public record AuthenticationResponse(
                String message,
                String token,
                String username,
                String email,
                LocalDate geboortedatum,
                String plaats) {
}
