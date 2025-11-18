package be.ucll.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import be.ucll.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByDormId(Long dormId);
}