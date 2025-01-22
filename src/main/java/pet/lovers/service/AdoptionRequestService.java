package pet.lovers.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pet.lovers.entities.AdoptionRequest;
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
    public void updateAdoptionRequest(AdoptionRequest adoptionRequest){
        adoptionRequestRepository.save(adoptionRequest);
    }

    public Optional<AdoptionRequest> findActiveById(Integer id) {
        return adoptionRequestRepository.findById(id).filter(this::hasApprovedAdopter);
    }

    public Optional<AdoptionRequest> findById(Integer id) {
        return adoptionRequestRepository.findById(id);
    }

    public List<AdoptionRequest> findByAdopterId(Integer adopterId) {
        return adoptionRequestRepository.findByAdopterId(adopterId);
    }

    public List<AdoptionRequest> findByShelterId(Integer shelterId) {
        return adoptionRequestRepository.findByShelterId(shelterId);
    }

    public boolean hasApprovedAdopter(AdoptionRequest adoptionRequest){
        return adoptionRequest.getAdopter().getUserStatus().equals(UserStatus.APPROVED);
    }

    public void save(AdoptionRequest adoptionRequest) {
        adoptionRequestRepository.save(adoptionRequest);
    }
}
