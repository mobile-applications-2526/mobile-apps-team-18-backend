package be.ucll.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import be.ucll.controller.dto.CreateExpenseDTO;
import be.ucll.exception.DormException;
import be.ucll.exception.UserException;
import be.ucll.model.Dorm;
import be.ucll.model.Expense;
import be.ucll.model.ExpenseShare;
import be.ucll.model.User;
import be.ucll.repository.ExpenseRepository;
import be.ucll.repository.ExpenseShareRepository;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseShareRepository expenseShareRepository;
    private final DormService dormService;
    private final UserService userService;

    public ExpenseService(ExpenseRepository expenseRepository,
            ExpenseShareRepository expenseShareRepository,
            DormService dormService,
            UserService userService) {
        this.expenseRepository = expenseRepository;
        this.expenseShareRepository = expenseShareRepository;
        this.dormService = dormService;
        this.userService = userService;
    }

    public Expense createExpense(String dormCode, CreateExpenseDTO dto, Authentication authentication) {
        User creator = userService.findByUsername(authentication.getName());
        Dorm dorm = dormService.getDormByCode(dormCode);

        if (!dorm.getUsers().contains(creator)) {
            throw new DormException("User not in dorm");
        }

        Expense expense = new Expense(dto.title(), dto.totalAmount(), creator);
        expense.setDorm(dorm);

        int count = dto.participantIds().size();
        double per = Math.round(dto.totalAmount() / count);

        for (Long uid : dto.participantIds()) {
            User u = userService.findById(uid);
            ExpenseShare s = new ExpenseShare(u, per);
            expense.addShare(s);
        }

        return expenseRepository.save(expense);
    }

    public List<Expense> getExpensesByDormId(Long dormId) {
        return expenseRepository.findByDormId(dormId);
    }

    public ExpenseShare markSharePaid(Long expenseId, Long userId, Authentication authentication) {
        ExpenseShare share = expenseShareRepository.findByUserId(userId)
                .stream()
                .filter(s -> s.getExpense().getId().equals(expenseId))
                .findFirst()
                .orElseThrow(() -> new UserException("Share not found"));

        share.setPaid(true);
        return expenseShareRepository.save(share);
    }
}