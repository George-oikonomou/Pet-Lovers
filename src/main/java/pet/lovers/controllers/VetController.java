package pet.lovers.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pet.lovers.entities.*;
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
    private final ShelterRequestService shelterRequestService;
    private final VetService vetService;

    public VetController(UserService userService, PetService petService, ShelterService shelterService, ShelterRequestService shelterRequestService, VetService vetService) {
        this.userService = userService;
        this.petService = petService;
        this.shelterService = shelterService;
        this.shelterRequestService = shelterRequestService;
        this.vetService = vetService;
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

    @GetMapping("/shelter-request")
    public String showShelterRequest(Model model) {
        Vet currentVet = (Vet) userService.getCurrentUser();

        List<Shelter> sheltersWithoutVet = shelterService.getShelters()
                .stream()
                .filter(shelter -> shelter.getVet() == null)
                .collect(Collectors.toList());

        model.addAttribute("shelters", sheltersWithoutVet);
        model.addAttribute("currentVet", currentVet);
        return "vet/shelter-request";
    }


    @PostMapping("/shelter-request")
    public String submitShelterRequest(@RequestParam("shelterId") int shelterId) {
        Vet vet = (Vet) userService.getCurrentUser();
        Shelter shelter = shelterService.getShelterById(shelterId);
        boolean exists = shelterRequestService.existsByVetAndShelter(vet, shelter);
        if (exists) {
            return "redirect:/vet/shelter-request";
        }
        ShelterRequest shelterRequest = new ShelterRequest(vet, shelter);
        shelterRequestService.saveShelterRequest(shelterRequest);
        return "redirect:/vet/shelter-request";
    }

    @GetMapping("/shelters")
    public String viewShelters(Model model) {
        Vet vet = (Vet) userService.getCurrentUser();
        List<Shelter> shelters = shelterService.findByVet(vet);
        model.addAttribute("shelters", shelters);
        return "vet/shelters";
    }
}