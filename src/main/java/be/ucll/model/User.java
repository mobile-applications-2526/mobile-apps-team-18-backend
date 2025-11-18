package be.ucll.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username should not be empty")
    private String username;

    @Email(message = "Please provide a valid mail address")
    private String email;

    @Past(message = "Geboortedatum must be in the past")
    private LocalDate geboortedatum;

    private String locatie;

    @NotBlank(message = "Password should not be empty")
    private String password;

    @ManyToMany
    @JsonBackReference
    private List<Event> joinedEvents = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "dorm_id")
    @JsonBackReference
    private Dorm dorm;

    protected User() {
    }

    public User(String username, String email, LocalDate geboortedatum, String locatie, String password) {
        setUsername(username);
        setEmail(email);
        setGeboortedatum(geboortedatum);
        setLocatie(locatie);
        setPassword(password);
    }

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Dorm getDorm() {
        return dorm;
    }

    public void setDorm(Dorm dorm) {
        this.dorm = dorm;
    }

    public List<Event> getJoinedEvents() {
        return joinedEvents;
    }

    public void setJoinedEvents(List<Event> joinedEvents) {
        this.joinedEvents = joinedEvents;
    }

    public void addEvent(Event event) {
        if (!this.joinedEvents.contains(event)) {
            this.joinedEvents.add(event);
            event.addParticipant(this);
        }
    }

    public void removeEvent(Event event) {
        if (this.joinedEvents.contains(event)) {
            this.joinedEvents.remove(event);
            event.removeParticipant(this);
        }
    }

}
