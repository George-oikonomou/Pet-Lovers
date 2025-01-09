package pet.lovers.controllers;

import pet.lovers.entities.*;
import pet.lovers.repositories.LocationRepository;
import pet.lovers.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pet.lovers.repositories.UserRepository;
import pet.lovers.service.UserService;

import java.time.LocalDateTime;

@Controller
public class AuthController {


    UserService userService;
    RoleRepository roleRepository;
    LocationRepository locationRepository;
    UserRepository userRepository;

    public AuthController(RoleRepository roleRepository, UserRepository userRepository, UserService userService, LocationRepository locationRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.locationRepository = locationRepository;
    }

    @PostConstruct
    public void setup() {
        Location locationAdopter = new Location("region", "city", "address", "123A", "12345");
        Location locationAdmin = new Location("region", "city", "address", "123A", "12345");
        Location locationShelter = new Location("region", "city", "address", "123A", "12345");
        Location locationVet = new Location("region", "city", "address", "123A", "12345");

        locationRepository.save(locationAdopter);
        locationRepository.save(locationAdmin);
        locationRepository.save(locationShelter);
        locationRepository.save(locationVet);


        Document document = new Document("path", "name", true, "descriptor");
        Document documentOfVet = new Document("path2", "name2", true, "descriptor2");
        Document documentOfShelter = new Document("path3", "name3", true, "descriptor3");

        // Save roles to ensure they are managed by the persistence context
        Role role_user = roleRepository.updateOrInsert(new Role("ROLE_USER"));
        Role role_adopter = roleRepository.updateOrInsert(new Role("ROLE_ADOPTER"));
        Role role_admin = roleRepository.updateOrInsert(new Role("ROLE_ADMIN"));
        Role role_vet = roleRepository.updateOrInsert(new Role("ROLE_VET"));
        Role role_shelter = roleRepository.updateOrInsert(new Role("ROLE_SHELTER"));

        // Create users
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User("admin", "admin@gmail.com", "admin", "0123456789", locationAdmin);
            admin.getRoles().add(role_admin);
            userService.saveUser(admin);
        }

        if (!userRepository.existsByUsername("vet")) {
            Vet vet = new Vet("vet", "vet@gmail.com", "vet", "1234567890", locationVet, "full name", "lorem ipsum", documentOfVet, null);
            vet.getRoles().add(role_vet);
            userService.saveUser(vet);
        }

        if (!userRepository.existsByUsername("shelter")) {
            Shelter shelter = new Shelter("shelter", "shelter@gmail.com", "shelter", "1345678902", locationShelter, "shelter name", documentOfShelter);
            shelter.getRoles().add(role_shelter);
            userService.saveUser(shelter);
        }

        if (!userRepository.existsByUsername("adopter")) {
            Adopter adopter = new Adopter("adopter", "adopter@gmail.com", "adopter", "1245678903", locationAdopter, "full name", LocalDateTime.now().minusYears(21), document);
            adopter.getRoles().add(role_adopter);
            userService.saveUser(adopter);
        }
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

}
