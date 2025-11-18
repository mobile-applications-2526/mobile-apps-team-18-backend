package be.ucll.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import be.ucll.controller.dto.CreateExpenseDTO;
import be.ucll.model.Expense;
import be.ucll.model.ExpenseShare;
import be.ucll.service.ExpenseService;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public List<Expense> getByDorm(@RequestParam Long dormId) {
        return expenseService.getExpensesByDormId(dormId);
    }

    @PostMapping("/{dormCode}")
    public Expense create(@PathVariable String dormCode, @RequestBody CreateExpenseDTO dto, Authentication authentication) {
        return expenseService.createExpense(dormCode, dto, authentication);
    }

    @PutMapping("/{expenseId}/shares/{userId}/paid")
    public ExpenseShare markPaid(@PathVariable Long expenseId, @PathVariable Long userId, Authentication authentication) {
        return expenseService.markSharePaid(expenseId, userId, authentication);
    }
}