package pet.lovers.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pet.lovers.entities.*;
import pet.lovers.service.EmploymentRequestService;
import pet.lovers.service.PetService;
import pet.lovers.service.ShelterService;
import pet.lovers.service.UserService;
import pet.lovers.service.VetService;

import java.util.List;

@Controller
@RequestMapping("/vet")
@PreAuthorize("hasRole('ROLE_VET')")
public class VetController {
    private final UserService userService;
    private final PetService petService;
    private final ShelterService shelterService;
    private final EmploymentRequestService employmentRequestService;
    private final VetService vetService;

    public VetController(UserService userService, PetService petService, ShelterService shelterService, EmploymentRequestService employmentRequestService, VetService vetService) {
        this.userService = userService;
        this.petService = petService;
        this.shelterService = shelterService;
        this.employmentRequestService = employmentRequestService;
        this.vetService = vetService;

     }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("vetShelters", shelterService.findByVet((Vet) userService.getCurrentUser()));
        return "index";
    }

    @GetMapping("/health-status")
    public String showPetStatus(Model model) {
        Vet vet = (Vet) userService.getCurrentUser();

        List<Pet> pets = petService.findByShelters(vet.getShelters())
                .stream()
                .filter(pet -> pet.getUserStatus() == UserStatus.APPROVED
                        && pet.getPetStatus() != PetStatus.ADOPTED
                        && pet.getShelter().getUserStatus() == UserStatus.APPROVED)
                .toList();

        model.addAttribute("pets", pets);
        model.addAttribute("healthStatuses", HealthStatus.values());
        return "vet/health-status";
    }


    @PostMapping("/health-status")
    public String updateHealthStatus(@RequestParam("petId") int petId, @RequestParam("healthStatus") String healthStatus, RedirectAttributes redirectAttributes) {
        petService.updateHealthStatus(petId, healthStatus);
        redirectAttributes.addFlashAttribute("msg", "Health status updated successfully");
        return "redirect:/vet/health-status";
    }

    @GetMapping("/employment-request")
    public String showEmploymentRequest(Model model) {
        Vet currentVet = (Vet) userService.getCurrentUser();

        List<Shelter> sheltersActiveWithoutVet = shelterService.getShelters()
                                                               .stream()
                                                               .filter(shelter -> shelter.getVet() == null)
                                                               .toList();

        model.addAttribute("shelters", sheltersActiveWithoutVet);
        model.addAttribute("currentVet", currentVet);
        return "vet/employment-request";
    }


    @PostMapping("/employment-request")
    public String submitEmploymentRequest(@RequestParam("shelterId") int shelterId) {
        Vet vet = (Vet) userService.getCurrentUser();

        try {
            Shelter shelter = shelterService.findActiveByUserId(shelterId).orElseThrow(IllegalArgumentException::new);

            if (employmentRequestService.existsByVetAndShelter(vet, shelter))
                return "redirect:/vet/employment-request?error=requestExists";

            EmploymentRequest employmentRequest = new EmploymentRequest(vet, shelter);
            vet.getEmploymentRequests().add(employmentRequest);
            shelter.getEmploymentRequests().add(employmentRequest);
            employmentRequestService.saveEmploymentRequest(employmentRequest);
            vetService.updateVet(vet);
            shelterService.updateShelter(shelter);
            return "redirect:/vet/employment-request?success=true";
        }catch (IllegalArgumentException e){
            return "redirect:/vet/employment-request?error=shelterNotFound";
        }
    }

    @GetMapping("/shelters")
    public String viewShelters(Model model) {
        Vet vet = (Vet) userService.getCurrentUser();
        List<Shelter> shelters = shelterService.findByVet(vet);

        model.addAttribute("shelters", shelters);
        return "vet/shelters";
    }

    @GetMapping("/shelters/{shelterId}/remove")
    public String removeShelter(@PathVariable Integer shelterId,RedirectAttributes redirectAttributes) {
        Vet vet = (Vet) userService.getCurrentUser();
        try {
            Shelter shelter = shelterService.findByUserId(shelterId).orElseThrow(IllegalArgumentException::new);
            EmploymentRequest request = employmentRequestService.findByVetAndShelter(vet, shelter).orElseThrow(IllegalArgumentException::new);

            shelter.setVet(null);
            vet.getShelters().remove(shelter);
            employmentRequestService.deleteEmploymentRequest(request);

            vetService.updateVet(vet);
            shelterService.updateShelter(shelter);
            redirectAttributes.addFlashAttribute("msg", "Shelter removed successfully");
        }catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("error", "Shelter not found");
        }

        return "redirect:/vet/shelters";
    }
}