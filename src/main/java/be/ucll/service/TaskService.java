package be.ucll.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import be.ucll.controller.dto.TaskDTO;
import be.ucll.exception.DormException;
import be.ucll.exception.TaskException;
import be.ucll.exception.UserException;
import be.ucll.model.Dorm;
import be.ucll.model.Task;
import be.ucll.model.User;
import be.ucll.repository.DormRepository;
import be.ucll.repository.TaskRepository;
import be.ucll.repository.UserRepository;
import be.ucll.types.TaskType;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final DormRepository dormRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, DormRepository dormRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.dormRepository = dormRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByDormId(Long dormId) {
        if (dormId == null) {
            throw new TaskException("DormId cannot be null or empty");
        }
        return taskRepository.findByDormId(dormId);
    }

    public Task createTask(String dormCode, Long assignedUserId, TaskDTO taskDTO, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserException("User not found"));

        User assigneduser = userRepository.findById(assignedUserId)
                .orElseThrow(() -> new UserException("AssignedUser not found"));

        Dorm dorm = dormRepository.findByCode(dormCode).orElseThrow(() -> new UserException("Dorm not found"));

        if (!dorm.getUsers().contains(assigneduser)) {
            throw new Error("User not in this dorm");
        }

        Task task = new Task(
                taskDTO.title(),
                taskDTO.description(),
                taskDTO.type(),
                taskDTO.date());

        task.setCreatedBy(user);
        task.setAssignedUser(assigneduser);

        if (!dorm.getUsers().contains(user)) {
            throw new TaskException("User is not authorized to create tasks for this dorm");
        }

        dorm.addTask(task);
        return taskRepository.save(task);
    }

    public String deleteTask(Long id) {
        taskRepository.deleteById(id);
        return "Task with ID " + id + " has been deleted successfully.";
    }

    public List<Task> getTaskByType(TaskType type) {
        return taskRepository.findByType(type);
    }

    public Task changeDone(Authentication authentication, Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new UserException("User not found"));
        Dorm dorm = dormRepository.findByUsers_Username(authentication.getName())
                .orElseThrow(() -> new UserException("You are not part of this dorm."));

        if (dorm == null) {
            throw new DormException("You are not part of this dorm.");
        }

        task.setDone(!task.isDone());

        return taskRepository.save(task);
    }

}