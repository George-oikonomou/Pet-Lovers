package pet.lovers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.lovers.entities.Vet;
import java.util.Optional;

@Repository
public interface VetRepository extends JpaRepository<Vet, Integer> {
    Optional<Vet> findById(Integer userId);
}