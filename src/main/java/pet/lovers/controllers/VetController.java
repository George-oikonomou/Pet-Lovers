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
import pet.lovers.service.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/vet")
@PreAuthorize("hasRole('ROLE_VET')")
public class VetController {
    private final UserService userService;
    private final PetService petService;
    private final ShelterService shelterService;

    public VetController(UserService userService, PetService petService, ShelterService shelterService) {
        this.userService = userService;
        this.petService = petService;
        this.shelterService = shelterService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {return "index";}

    @GetMapping("/health-status")
    public String showPetStatus(Model model) {
        Vet vet = (Vet) userService.getCurrentUser();

        List<Pet> pets = shelterService.findByVet(vet)
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