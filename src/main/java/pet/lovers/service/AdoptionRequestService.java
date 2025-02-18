package pet.lovers.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pet.lovers.entities.*;
import pet.lovers.repositories.AdoptionRequestRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdoptionRequestService {
    private final AdoptionRequestRepository adoptionRequestRepository;

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

    public List<AdoptionRequest> findActiveByPetId(Integer petId) {
        return adoptionRequestRepository.findByPetId(petId).stream().filter(this::hasApprovedAdopter).toList();
    }
    public boolean existsPendingRequest(Adopter adopter, Pet pet) {
        List<AdoptionRequest> requests = adoptionRequestRepository.findByAdopterAndPet(adopter, pet);

        return requests.stream().anyMatch(req -> req.getRequestStatus() == UserStatus.PENDING);
    }

    public boolean hasApprovedAdopter(AdoptionRequest adoptionRequest){
        return adoptionRequest.getAdopter().getUserStatus().equals(UserStatus.APPROVED);
    }

    public void save(AdoptionRequest adoptionRequest) {
        adoptionRequestRepository.save(adoptionRequest);
    }

    public void delete(AdoptionRequest adoptionRequest) {
        adoptionRequestRepository.delete(adoptionRequest);
    }
}