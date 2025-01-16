package pet.lovers.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestParam;
import pet.lovers.entities.*;
import pet.lovers.service.EmailService;
import pet.lovers.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Controller
public class UserController {

    private final EmailService emailService;
    @Value("${google.api.key}")
    private String googleApiKey;

    private final UserService userService;

    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/register/adopter")
    public String registerAdopter(Model model) {
        Adopter adopter = new Adopter();
        model.addAttribute("googleApiKey", googleApiKey);
        model.addAttribute("adopter", adopter);
        return "auth/register-adopter";
    }

    @PostMapping("/register/adopter")
    public String saveAdopter(@ModelAttribute Adopter adopter, Model model) {
        System.out.println("Adopter: " + adopter);
        Integer id = userService.saveUser(adopter);
        String message = "Adopter '" + id + "' saved successfully !";
        emailService.sendRegistrationMessageToUser(adopter.getEmail(),adopter.getFullName());
        model.addAttribute("msg", message);
        return "index";
    }


    @GetMapping("/register/shelter")
    public String registerShelter(Model model) {
        Shelter shelter = new Shelter();
        model.addAttribute("googleApiKey", googleApiKey);
        model.addAttribute("shelter", shelter);
        return "auth/register-shelter";
    }

    @PostMapping("/register/shelter")
    public String saveShelter(@ModelAttribute Shelter shelter,  Model model) {
        System.out.println("Shelter: " + shelter);
        Integer id = userService.saveUser(shelter);
        String message = "Shelter '" + id + "' saved successfully !";
        emailService.sendRegistrationMessageToUser(shelter.getEmail(),shelter.getName());
        model.addAttribute("msg", message);
        return "index";
    }


    @GetMapping("/register/vet")
    public String registerVet(Model model) {
        Vet vet = new Vet();
        model.addAttribute("googleApiKey", googleApiKey);
        model.addAttribute("vet", vet);
        return "auth/register-vet";
    }

    @PostMapping("/register/vet")
    public String saveVet(@ModelAttribute Vet vet, Model model){
        System.out.println("Roles: "+vet.getRoles());
        Integer id = userService.saveUser(vet);
        String message = "User '"+id+"' saved successfully !";
        emailService.sendRegistrationMessageToUser(vet.getEmail(),vet.getFullName());
        model.addAttribute("msg", message);
        return "index";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String sendResetPasswordEmail(@RequestParam("email") String email, Model model) {
        if (!userService.existsByEmail(email) ) {
            model.addAttribute("error", "No user found with the given email!");
            return "auth/forgot-password";
        }

        User user = userService.getUserByEmail(email);
        String tmpPassword = userService.getUsersTmpGeneratedVerificationCode(user);


        emailService.sendResetPasswordMessage(email,user.getUsername(),tmpPassword);
        model.addAttribute("msg", "Password reset email sent successfully!<br>Please check your email.<br>Verification code is valid for 5 minutes.");
        return "auth/reset-password";
    }

    @PostMapping("/reset-password")
    public String sendSuccessResetPasswordsEmail(@RequestParam("verification_Code") String verificationCode, @RequestParam("password") String password,@RequestParam("confirm_password") String confirmPassword, Model model) {
        try {
            User user = userService.getUserByVerificationCode(verificationCode);

            String error = userService.resetUserPassword(user, password, confirmPassword);
            if (error != null) {
                model.addAttribute("error", error);
                return "auth/reset-password";
            }

            emailService.sendSuccessResetPasswordMessage(user.getEmail(), user.getUsername());

            model.addAttribute("msg", "Password reset was successful!");
            return "auth/login";
        } catch (NoSuchElementException e) {
            model.addAttribute("error", "Invalid verification code!");
            return "auth/reset-password";
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
            return "auth/reset-password";
        }
    }
}