package be.ucll.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record DormInputDTO(@NotBlank(message = "Code is required") String name) {

}
