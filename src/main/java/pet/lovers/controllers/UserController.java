package pet.lovers.controllers;

import org.springframework.beans.factory.annotation.Value;
import pet.lovers.entities.*;
import pet.lovers.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {


    @Value("${google.api.key}")
    private String googleApiKey;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        Adopter adopter = new Adopter();
        model.addAttribute("googleApiKey", googleApiKey);
        model.addAttribute("adopter", adopter);
        return "auth/register";
    }

    @PostMapping("/saveAdopter")
    public String saveAdopter(@ModelAttribute Adopter adopter, Model model) {
        System.out.println("Adopter: " + adopter);
        Integer id = userService.saveUser(adopter);
        String message = "Adopter '" + id + "' saved successfully !";
        model.addAttribute("msg", message);
        return "index";
    }

    @PostMapping("/saveShelter")
    public String saveShelter(@ModelAttribute Shelter shelter,  Model model) {
        System.out.println("Shelter: " + shelter);
        Integer id = userService.saveUser(shelter);
        String message = "Shelter '" + id + "' saved successfully !";
        model.addAttribute("msg", message);
        return "index";
    }

    @PostMapping("/saveVet")
    public String saveUser(@ModelAttribute User user, Model model){
        System.out.println("Roles: "+user.getRoles());
        Integer id = userService.saveUser(user);
        String message = "User '"+id+"' saved successfully !";
        model.addAttribute("msg", message);
        return "index";
    }

    @GetMapping("/register/vet")
    public String vetRegister(Model model) {
        Vet vet = new Vet();
        model.addAttribute("vet", vet);
        return "auth/vetRegister";
    }

    @GetMapping("/register/shelter")
    public String shelterRegister(Model model) {
        Shelter shelter = new Shelter();
        model.addAttribute("googleApiKey", googleApiKey);
        model.addAttribute("shelter", shelter);
        return "auth/shelterRegister";
    }
}
