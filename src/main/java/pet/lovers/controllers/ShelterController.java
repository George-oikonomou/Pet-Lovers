package pet.lovers.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pet.lovers.entities.Pet;
import pet.lovers.entities.Shelter;
import pet.lovers.repositories.RoleRepository;
import pet.lovers.service.*;

@Controller
@RequestMapping("/shelter")
@PreAuthorize("hasRole('ROLE_SHELTER')")
public class ShelterController {
    private final ShelterService shelterService;
    UserService userService;
    RoleRepository roleRepository;
    PetService petService;
    VetService vetService;
    AdoptionRequestService adoptionRequestService;

    public ShelterController(UserService userService, RoleRepository roleRepository, PetService petService, VetService vetService, AdoptionRequestService adoptionRequestService, ShelterService shelterService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.petService = petService;
        this.vetService = vetService;
        this.adoptionRequestService = adoptionRequestService;
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

    //View Adoption Requests
    @GetMapping("/adoption-requests")
    public String viewAdoptions(Model model) {
        model.addAttribute("adoptionRequests", adoptionRequestService.getAdoptionRequests());
        return "shelter/adoption-requests";
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
        System.out.println("Pet: " + pet);
        Integer id = petService.savePet(pet);
        String message = "Pet '"+id+"' saved successfully !";
        model.addAttribute("msg", message);
        return "index";
    }
}