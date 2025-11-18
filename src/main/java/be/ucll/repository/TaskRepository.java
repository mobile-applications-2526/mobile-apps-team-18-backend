package be.ucll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.ucll.model.Task;
import be.ucll.types.TaskType;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByType(TaskType type);

    List<Task> findByDormId(Long dormId);

}
