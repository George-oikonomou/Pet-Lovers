package pet.lovers.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pet.lovers.entities.AdoptionRequest;
import pet.lovers.entities.Shelter;
import pet.lovers.entities.UserStatus;
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

    @Transactional
    public Integer updateAdoptionRequest(AdoptionRequest adoptionRequest){
        adoptionRequestRepository.save(adoptionRequest);
        return adoptionRequest.getId();
    }

    public Optional<AdoptionRequest> findById(Integer id) {
        return adoptionRequestRepository.findById(id);
    }

    public List<AdoptionRequest> findByAdopterId(Integer adopterID) {
        return adoptionRequestRepository.findByAdopterId(adopterID);
    }

    public List<AdoptionRequest> findByShelter(Shelter shelter) {
        return adoptionRequestRepository.findByShelter(shelter);
    }

    public void save(AdoptionRequest adoptionRequest) {
        adoptionRequestRepository.save(adoptionRequest);
    }
}
