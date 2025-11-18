package be.ucll.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import be.ucll.model.ExpenseShare;

public interface ExpenseShareRepository extends JpaRepository<ExpenseShare, Long> {
    List<ExpenseShare> findByUserId(Long userId);
}