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

//    @Transactional
//    public List<AdoptionRequest> getAdoptionRequestsByUserStatus(UserStatus status){
//        return adoptionRequestRepository.findByRequestStatus(status);
//    }

}
