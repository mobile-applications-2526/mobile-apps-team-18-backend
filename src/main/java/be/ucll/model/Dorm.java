package be.ucll.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "dorms")
public class Dorm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Dorm name may not be empty")
    private String name;

    @NotBlank(message = "Code may not be empty")
    private String code;

    @OneToMany(mappedBy = "dorm")
    @JsonManagedReference
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "dorm")
    @JsonManagedReference
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "dorm")
    @JsonManagedReference
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "dorm")
    @JsonManagedReference
    private List<Expense> expenses = new ArrayList<>();

    protected Dorm() {
    }

    public Dorm(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void addUser(User user) {
        this.users.add(user);
        user.setDorm(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.setDorm(null);
    }

    public void addTask(Task task) {
        this.tasks.add(task);
        task.setDorm(this);
    }

    public void addEvent(Event event) {
        this.events.add(event);
        event.setDorm(this);
    }

}
