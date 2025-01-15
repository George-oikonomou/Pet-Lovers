package pet.lovers.service;

import org.springframework.stereotype.Service;
import pet.lovers.repositories.VetRepository;

@Service
public class VetService {

    private VetRepository vetRepository;

    public VetService(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }
}