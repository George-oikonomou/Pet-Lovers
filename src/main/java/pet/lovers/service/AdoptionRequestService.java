package pet.lovers.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pet.lovers.entities.AdoptionRequest;
import pet.lovers.repositories.AdoptionRequestRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdoptionRequestService {
    private AdoptionRequestRepository adoptionRequestRepository;

    public AdoptionRequestService(AdoptionRequestRepository adoptionRequestRepository) {
        this.adoptionRequestRepository = adoptionRequestRepository;
    }

    @Transactional
    public List<AdoptionRequest> getAdoptionRequests(){
        return adoptionRequestRepository.findAll();
    }

}
