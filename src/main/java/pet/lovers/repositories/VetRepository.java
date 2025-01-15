package pet.lovers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.lovers.entities.UserStatus;
import pet.lovers.entities.Vet;

import java.util.List;

@Repository
public interface VetRepository extends JpaRepository<Vet, Integer> {
    Vet findByUsername(String username);
}
