package be.ucll.controller.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateExpenseDTO(
    @NotBlank String title,
    @NotNull Double totalAmount,
    @NotNull List<Long> participantIds
) {}