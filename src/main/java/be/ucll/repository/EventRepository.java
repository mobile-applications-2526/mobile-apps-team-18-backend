package be.ucll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.ucll.model.Event;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

	List<Event> findByDormId(Long dormId);
}
