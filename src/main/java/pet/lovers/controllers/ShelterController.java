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
import pet.lovers.service.*;

@Controller
@RequestMapping("/shelter")
@PreAuthorize("hasRole('ROLE_SHELTER')")
public class ShelterController {
    private final UserService userService;
    private final PetService petService;

    public ShelterController(UserService userService, PetService petService) {
        this.userService = userService;
        this.petService = petService;
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
        System.out.println("Pet: " + pet);
        Integer id = petService.savePet(pet);
        String message = "Pet '"+id+"' saved successfully !";
        model.addAttribute("msg", message);
        return "index";
    }
}