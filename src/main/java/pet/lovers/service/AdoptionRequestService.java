package pet.lovers.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pet.lovers.entities.AdoptionRequest;
import pet.lovers.entities.UserStatus;
import pet.lovers.repositories.AdoptionRequestRepository;

import java.util.List;

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

    @Transactional
    public Integer updateAdoptionRequest(AdoptionRequest adoptionRequest){
        adoptionRequestRepository.save(adoptionRequest);
        return adoptionRequest.getId();
    }

}
