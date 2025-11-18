package be.ucll.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import be.ucll.model.Dorm;

public interface DormRepository extends JpaRepository<Dorm, Long> {

    Optional<Dorm> findByCode(String code);

    Optional<Dorm> findByUsers_Id(Long id);

    Optional<Dorm> findByUsers_Username(String username);

}
