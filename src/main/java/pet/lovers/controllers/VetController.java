package pet.lovers.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pet.lovers.entities.HealthStatus;
import pet.lovers.entities.Pet;
import pet.lovers.entities.UserStatus;
import pet.lovers.entities.Vet;
import pet.lovers.repositories.ShelterRepository;
import pet.lovers.service.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/vet")
@PreAuthorize("hasRole('ROLE_VET')")
public class VetController {
    private final VetService vetService;
    ShelterService shelterService;
    UserService userService;
    PetService petService;
    AdoptionRequestService adoptionRequestService;
    private final ShelterRepository shelterRepository;

    public VetController(VetService vetService, UserService userService, PetService petService, AdoptionRequestService adoptionRequestService, ShelterService shelterService, ShelterRepository shelterRepository) {
        this.vetService = vetService;
        this.userService = userService;
        this.petService = petService;
        this.adoptionRequestService = adoptionRequestService;
        this.shelterService = shelterService;
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

}