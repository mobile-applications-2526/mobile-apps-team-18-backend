package be.ucll.controller.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record UserPongDTO(
        @NotBlank(message = "Id is required") Long id,
        @NotBlank(message = "Username is required") String username,
        @Email(message = "Invalid email format") String email,
        @NotNull(message = "Geboortedatum should not be empty") @Past(message = "Geboortedatum must be in the past") LocalDate geboortedatum,
        @NotBlank(message = "Locatie is required") String locatie) {
}
