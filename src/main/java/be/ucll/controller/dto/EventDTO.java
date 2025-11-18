package be.ucll.controller.dto;

import java.time.LocalDate;

public record EventDTO(String name, String description, String location, LocalDate date) {
}
