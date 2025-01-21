package pet.lovers.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pet.lovers.entities.*;
import pet.lovers.service.EmploymentRequestService;
import pet.lovers.service.PetService;
import pet.lovers.service.ShelterService;
import pet.lovers.service.UserService;
import java.util.List;

@Controller
@RequestMapping("/vet")
@PreAuthorize("hasRole('ROLE_VET')")
public class VetController {
    private final UserService userService;
    private final PetService petService;
    private final ShelterService shelterService;
    private final EmploymentRequestService employmentRequestService;

    public VetController(UserService userService, PetService petService, ShelterService shelterService, EmploymentRequestService employmentRequestService) {
        this.userService = userService;
        this.petService = petService;
        this.shelterService = shelterService;
        this.employmentRequestService = employmentRequestService;
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
                                       .toList();

        model.addAttribute("pets", pets);
        model.addAttribute("healthStatuses", HealthStatus.values());
        return "vet/health-status";
    }


    @PostMapping("/health-status")
    public String updateHealthStatus(@RequestParam("petId") int petId, @RequestParam("healthStatus") String healthStatus) {
        petService.updateHealthStatus(petId, healthStatus);
        return "redirect:/vet/health-status";
    }

    @GetMapping("/employment-request")
    public String showEmploymentRequest(Model model) {
        Vet currentVet = (Vet) userService.getCurrentUser();

        List<Shelter> sheltersWithoutVet = shelterService.getShelters()
                                                         .stream()
                                                         .filter(shelter -> shelter.getVet() == null)
                                                         .toList();

        model.addAttribute("shelters", sheltersWithoutVet);
        model.addAttribute("currentVet", currentVet);
        return "vet/employment-request";
    }


    @PostMapping("/employment-request")
    public String submitEmploymentRequest(@RequestParam("shelterId") int shelterId) {
        Vet vet = (Vet) userService.getCurrentUser();
        Shelter shelter = shelterService.getShelterById(shelterId);

        if (employmentRequestService.existsByVetAndShelter(vet, shelter))
            return "redirect:/vet/employment-request?error=requestExists";

        EmploymentRequest employmentRequest = new EmploymentRequest(vet, shelter);
        employmentRequestService.saveEmploymentRequest(employmentRequest);
        return "redirect:/vet/employment-request";
    }

    @GetMapping("/shelters")
    public String viewShelters(Model model) {
        Vet vet = (Vet) userService.getCurrentUser();
        List<Shelter> shelters = shelterService.findByVet(vet);
        model.addAttribute("shelters", shelters);
        return "vet/shelters";
    }
}