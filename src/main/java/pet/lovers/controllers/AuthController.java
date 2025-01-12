package pet.lovers.controllers;

import pet.lovers.entities.*;
import pet.lovers.repositories.AdoptionRequestRepository;
import pet.lovers.repositories.PetRepository;
import pet.lovers.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pet.lovers.repositories.UserRepository;
import pet.lovers.service.PetService;
import pet.lovers.service.UserService;;
import java.time.LocalDate;

@Controller
public class AuthController {

    UserService userService;
    RoleRepository roleRepository;
    UserRepository userRepository;
    PetRepository petRepository;
    PetService petService;
    AdoptionRequestRepository adoptionRequestRepository;

    public AuthController(RoleRepository roleRepository, UserRepository userRepository, UserService userService, PetRepository petRepository, PetService petService, AdoptionRequestRepository adoptionRequestRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.petRepository = petRepository;
        this.petService = petService;
        this.adoptionRequestRepository = adoptionRequestRepository;
    }

    @PostConstruct
    public void setup() {
        Document document = new Document("path", true);
        Document documentOfVet = new Document("path2", true);
        Document documentOfShelter = new Document("path3",true);

        // Save roles to ensure they are managed by the persistence context
        Role role_user = roleRepository.updateOrInsert(new Role("ROLE_USER"));
        Role role_adopter = roleRepository.updateOrInsert(new Role("ROLE_ADOPTER"));
        Role role_admin = roleRepository.updateOrInsert(new Role("ROLE_ADMIN"));
        Role role_vet = roleRepository.updateOrInsert(new Role("ROLE_VET"));
        Role role_shelter = roleRepository.updateOrInsert(new Role("ROLE_SHELTER"));

        // Create users
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User("admin", "admin@gmail.com", "admin", "0123456789", "location");
            admin.getRoles().add(role_admin);
            userService.saveUser(admin);
        }

        Adopter adopter = null;
        Shelter shelter = null;

        if (!userRepository.existsByUsername("vet")) {
            Vet vet = new Vet("vet", "vet@gmail.com", "vet", "1234567890", "location", "full name", "lorem ipsum", documentOfVet, null);
            vet.getRoles().add(role_vet);
            userService.saveUser(vet);
        }

        if (!userRepository.existsByUsername("shelter")) {
            shelter = new Shelter("shelter", "shelter@gmail.com", "shelter", "1345678902", "location", "shelter name", documentOfShelter);

            shelter.getRoles().add(role_shelter);
            userService.saveUser(shelter);

            if (!petRepository.existsByName("Rocky")) {
                Pet rocky = new Pet("Rocky", "male", shelter, 2018, "Dog", "Lab", 10.0);
                petService.savePet(rocky);
            }
            if (!petRepository.existsByName("Meow")) {
                Pet meow = new Pet("Meow", "female", shelter, 2023, "Cat", "Unknown", 3.5);
                petService.savePet(meow);
            }
        }

        if (!userRepository.existsByUsername("adopter")) {
             adopter = new Adopter("adopter", "adopter@gmail.com", "adopter", "1245678903", "location", "full name", LocalDate.now().minusYears(21), document);
            adopter.getRoles().add(role_adopter);
            userService.saveUser(adopter);
        }

    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}
