package pet.lovers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.lovers.entities.Shelter;
import pet.lovers.entities.UserStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Integer> {
    List<Shelter> findByUserStatus(UserStatus status);
}
