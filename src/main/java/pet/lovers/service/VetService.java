package pet.lovers.service;

import org.springframework.stereotype.Service;
import pet.lovers.entities.Vet;
import pet.lovers.repositories.VetRepository;

import java.util.Optional;

@Service
public class VetService {

    private VetRepository vetRepository;

    public VetService(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    public Vet getVetById(Integer vetRequestId) {
        Optional<Vet> vet = vetRepository.findById(vetRequestId);
        return vet.orElse(null);
    }

    public void updateVet(Vet selectedVet) {
        vetRepository.save(selectedVet);
    }
}