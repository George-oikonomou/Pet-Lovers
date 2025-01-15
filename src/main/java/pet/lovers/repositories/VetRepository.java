package pet.lovers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.lovers.entities.Vet;

@Repository
public interface VetRepository extends JpaRepository<Vet, Integer> {
}