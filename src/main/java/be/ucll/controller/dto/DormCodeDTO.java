package be.ucll.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record DormCodeDTO(
        @NotBlank(message = "Code is required") String code) {
}
