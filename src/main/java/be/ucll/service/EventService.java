package be.ucll.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import be.ucll.controller.dto.EventDTO;
import be.ucll.exception.EventException;
import be.ucll.exception.UserException;
import be.ucll.model.Dorm;
import be.ucll.model.Event;
import be.ucll.model.User;
import be.ucll.repository.DormRepository;
import be.ucll.repository.EventRepository;
import be.ucll.repository.UserRepository;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final DormRepository dormRepository;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, DormRepository dormRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.dormRepository = dormRepository;
        this.userRepository = userRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getEventsByDormId(Long dormId) {
        if (dormId == null) {
            throw new EventException("DormId cannot be null or empty");
        }
        return eventRepository.findByDormId(dormId);
    }

    public Event createEvent(String dormCode, EventDTO eventInput, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserException("User not found"));
        Dorm dorm = dormRepository.findByCode(dormCode).orElseThrow(() -> new UserException("Dorm not found"));

        Event event = new Event(
                eventInput.name(),
                eventInput.description(),
                eventInput.location(),
                eventInput.date(),
                user);

        if (!dorm.getUsers().contains(user)) {
            throw new EventException("User is not authorized to create events for this dorm");
        }

        dorm.addEvent(event);
        return eventRepository.save(event);
    }

    public String deleteEvent(Long id) {
        eventRepository.deleteById(id);
        return "Event with ID " + id + " deleted successfully";
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventException("Event with ID " + id + " not found"));
    }

    public Event joinEvent(Authentication authentication, Long eventId) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserException("User not found"));

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventException("Event not found"));

        if (!user.getDorm().getEvents().contains(event)) {
            throw new Error("This event doesn't belong to this dorm");
        }

        if (user.getJoinedEvents().contains(event)) {
            user.removeEvent(event);
        }

        else {
            user.addEvent(event);
        }

        userRepository.save(user);
        return eventRepository.save(event);
    }
}
