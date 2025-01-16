package pet.lovers.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pet.lovers.entities.*;
import pet.lovers.repositories.ShelterRepository;
import pet.lovers.repositories.ShelterRequestRepository;
import pet.lovers.repositories.VetRepository;
import pet.lovers.service.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/vet")
@PreAuthorize("hasRole('ROLE_VET')")
public class VetController {
    ShelterService shelterService;
    UserService userService;
    PetService petService;
    VetRepository vetRepository;
    ShelterRequestRepository shelterRequestRepository;
    AdoptionRequestService adoptionRequestService;
    private final ShelterRepository shelterRepository;

    public VetController(ShelterService shelterService, UserService userService, PetService petService, VetRepository vetRepository, ShelterRequestRepository shelterRequestRepository, AdoptionRequestService adoptionRequestService, ShelterRepository shelterRepository) {
        this.shelterService = shelterService;
        this.userService = userService;
        this.petService = petService;
        this.vetRepository = vetRepository;
        this.shelterRequestRepository = shelterRequestRepository;
        this.adoptionRequestService = adoptionRequestService;
        this.shelterRepository = shelterRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard() {return "index";}

    @GetMapping("/health-status")
    public String showPetStatus(Model model) {
        Vet vet = (Vet) userService.getCurrentUser();

        List<Pet> pets = shelterRepository.findByVet(vet)
                .stream()
                .flatMap(shelter -> shelter.getPets().stream())
                .filter(pet -> pet.getUserStatus() == UserStatus.APPROVED)
                .collect(Collectors.toList());

        model.addAttribute("pets", pets);
        model.addAttribute("healthStatuses", HealthStatus.values());
        return "vet/health-status";
    }


    @PostMapping("/health-status")
    public String updateHealthStatus(@RequestParam("petId") int petId,
                                     @RequestParam("healthStatus") String healthStatus) {
        petService.updateHealthStatus(petId, healthStatus);
        return "redirect:/vet/health-status";
    }

    @GetMapping("/shelter-request-submission")
    public String showShelterRequestSubmission(Model model) {
        model.addAttribute("shelters", shelterService.getShelters());
        return "vet/shelter-request-submission";
    }

    @PostMapping("/shelter-request-submission")
    public String submitShelterRequest(@RequestParam("shelterId") int shelterId) {
        Vet vet = (Vet) userService.getCurrentUser();

        // Fetch the shelter
        Shelter shelter = shelterRepository.findById(shelterId);

        // Check if the vet has already requested this shelter
        if (shelterRequestRepository.existsByVetAndShelter(vet, shelter)) {
            return "redirect:/vet/shelter-request-submission?error=alreadyRequested";
        }

        // Create a new ShelterRequest and set the status to PENDING
        ShelterRequest shelterRequest = new ShelterRequest();
        shelterRequest.setVet(vet);
        shelterRequest.setShelter(shelter);
        // Save the ShelterRequest
        shelterRequestRepository.save(shelterRequest);

        // Redirect back with success message
        return "redirect:/vet/shelter-request-submission?success=true";
    }


}