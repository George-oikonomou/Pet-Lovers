package pet.lovers.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pet.lovers.entities.*;
import pet.lovers.service.*;

import java.util.List;

@Controller
@RequestMapping("/shelter")
@PreAuthorize("hasRole('ROLE_SHELTER')")
public class ShelterController {
    private final UserService userService;
    private final PetService petService;
    private final EmploymentRequestService employmentRequestService;
    private final VetService vetService;
    private final ShelterService shelterService;

    public ShelterController(UserService userService, PetService petService, EmploymentRequestService employmentRequestService, VetService vetService, ShelterService shelterService) {
        this.userService = userService;
        this.petService = petService;
        this.employmentRequestService = employmentRequestService;
        this.vetService = vetService;
        this.shelterService = shelterService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "index";
    }


    //View Pets
    @GetMapping("/pets")
    public String showPets(Model model) {
        model.addAttribute("pets", petService.getPets());
        return "shelter/pets";
    }

    //Register New Pet
    @GetMapping("/new-pet")
    public String newPet(Model model) {
        Pet pet = new Pet();
        model.addAttribute("newPet", pet);
        return "shelter/new-pet";
    }

    @PostMapping("/new-pet")
    public String savePet(@ModelAttribute Pet pet, Model model){
        Shelter shelter = (Shelter) userService.getCurrentUser();
        pet.setShelter(shelter);
        Integer id = petService.savePet(pet);
        String message = "Pet '"+id+"' saved successfully !";
        model.addAttribute("msg", message);
        return "index";
    }

    @GetMapping("/vet-review")
    public String showVetReviewPage(Model model) {
        Shelter shelter = (Shelter) userService.getCurrentUser();
        List<EmploymentRequest> employmentRequests = shelter.getEmploymentRequests();
        model.addAttribute("pendingVets", employmentRequests);
        return "shelter/vet-review";
    }

    @PostMapping("/vet-review")
    public String selectVet(@RequestParam("vetRequestId") Integer vetRequestId) {
        Shelter shelter = (Shelter) userService.getCurrentUser();
        Vet selectedVet = vetService.getVetById(vetRequestId);

        shelter.setVet(selectedVet);
        selectedVet.getShelters().add(shelter);

        shelterService.updateShelter(shelter);
        vetService.updateVet(selectedVet);

        shelter.getEmploymentRequests().forEach(request -> {
            UserStatus status = request.getVet().getId().equals(vetRequestId)
                    ? UserStatus.APPROVED
                    : UserStatus.REJECTED;
            request.setStatus(status);
            employmentRequestService.deleteEmploymentRequest(request);
        });

        return "redirect:/shelter/vet-review?success";
    }
}