package be.ucll.controller.dto;

import java.time.LocalDate;

import be.ucll.types.TaskType;

public record TaskDTO(String title, String description, TaskType type, LocalDate date) {
    
}
