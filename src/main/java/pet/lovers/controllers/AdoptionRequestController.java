package pet.lovers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pet.lovers.entities.AdoptionRequest;
import pet.lovers.service.AdoptionRequestService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adoption-requests")
public class AdoptionRequestController {

    private final AdoptionRequestService adoptionRequestService;

    @Autowired
    public AdoptionRequestController(AdoptionRequestService adoptionRequestService) {
        this.adoptionRequestService = adoptionRequestService;
    }


    @GetMapping
    public List<AdoptionRequest> getAllAdoptionRequests() {
        return adoptionRequestService.getAdoptionRequests();
    }

    // Endpoint to get adoption requests for a specific shelter
    @GetMapping("/shelter/{shelterId}")
    public Optional<AdoptionRequest> getAdoptionRequestsByShelter(@PathVariable Integer shelterId) {
        return adoptionRequestService.getAdoptionRequestsShelter(shelterId);
    }
}
