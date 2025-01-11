package pet.lovers.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import pet.lovers.entities.*;
import pet.lovers.repositories.RoleRepository;
import pet.lovers.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {


    @Value("${google.api.key}")
    private String googleApiKey;

    private UserService userService;

    private RoleRepository roleRepository;

    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        Adopter adopter = new Adopter();
        model.addAttribute("googleApiKey", googleApiKey);
        model.addAttribute("adopter", adopter);
        return "auth/register";
    }

    @PostMapping("/saveAdopter")
    public String saveAdopter(@ModelAttribute @Valid Adopter adopter, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println("Validation errors: " + result.getAllErrors());
            return "auth/register";
        }
        System.out.println("Adopter: " + adopter);
        Integer id = userService.saveUser(adopter);
        String message = "User '" + id + "' saved successfully !";
        model.addAttribute("msg", message);
        return "index";
    }

    @PostMapping("/saveUser")
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
        model.addAttribute("shelter", shelter);
        return "auth/shelterRegister";
    }
}
