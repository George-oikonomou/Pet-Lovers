package pet.lovers.controllers;

import pet.lovers.entities.*;
import pet.lovers.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import pet.lovers.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
public class SetupController {
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final PetService petService;


    public SetupController(RoleRepository roleRepository, UserService userService, PetService petService, VetService vetService, AdoptionRequestService adoptionRequestService, VisitService visitService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.petService = petService;
    }

    @PostConstruct
    public void setup() {
        roleRepository.updateOrInsert(new Role(Role.ADOPTER));
        Role roleAdmin = roleRepository.updateOrInsert(new Role(Role.ADMIN));
        roleRepository.updateOrInsert(new Role(Role.VET));
        roleRepository.updateOrInsert(new Role(Role.SHELTER));

        if (!userService.existsByEmail("admin@gmail.com")) {
            User admin = new User("admin", "admin@gmail.com", "admin", "0123456789", "location","path");
            admin.getRoles().add(roleAdmin);
            userService.saveUser(admin);
        }

        Adopter adopter;
        Shelter shelter;
        Vet vet;
        Shelter NotApprovedShelter;

        if (!userService.existsByEmail("vet@gmail.com")) {
            vet = new Vet("vet", "vet@gmail.com", "vet", "1234567890", "Athens", "Vet Doe",  "https://www.4icu.org/i/programs-courses-degrees/bachelor-of-veterinary-medicine-500x356.png");
            vet.setUserStatus(UserStatus.APPROVED);
            userService.saveUser(vet);
        }

        if (!userService.existsByEmail("NotApprovedShelter@gmail.com")) {
            NotApprovedShelter = new Shelter("NotApprovedShelter", "NotApprovedShelter@gmail.com", "NotApprovedShelter", "1555678932", "Athens", "Pet Refuge", "https://www.luckydogrefuge.com/uploads/1/4/2/2/142271538/editor/dept-of-agriculture-animal-shelter-asf-000019.png?1699560754");
            userService.saveUser(NotApprovedShelter);
        }

        if (!userService.existsByEmail("shelter@gmail.com")) {
            shelter = new Shelter("shelter", "shelter@gmail.com", "shelter", "1345678902", "Athens", "Lucky Dog Refuge", "https://www.luckydogrefuge.com/uploads/1/4/2/2/142271538/editor/dept-of-agriculture-animal-shelter-asf-000019.png?1699560754");
            shelter.setUserStatus(UserStatus.APPROVED);
            userService.saveUser(shelter);

            if (!petService.existsByName("Rocky")) {
                Pet rocky = new Pet("Rocky", "male", shelter, 2018, "Dog", "Lab", 10.0, "https://animalcorner.org/wp-content/uploads/2020/04/Morkie-2-1024x819.jpg");
                petService.savePet(rocky);
            }
            if (!petService.existsByName("Meow")) {
                Pet meow = new Pet("Meow", "female", shelter, 2023, "Cat", "Unknown", 3.5, "https://www.nekocatcafe.com/asset/677c5b1eca829/Front%20Page%201.2025.jpg");
                petService.savePet(meow);
            }

            if (!petService.existsByName("Gato")) {
                Pet gato = new Pet("Gato", "female", shelter, 2023, "Cat", "Khao Mane", 3.5, "https://cdn.shopify.com/s/files/1/0997/4496/articles/Screen_Shot_2018-08-22_at_4.07.39_PM_e6921cc3-5567-457e-96ad-4c6fbd83413a.png?v=1730770650");
                gato.setHealthStatus(HealthStatus.HEALTHY);
                gato.setUserStatus(UserStatus.APPROVED);
                petService.savePet(gato);
            }
        }

        if (!userService.existsByEmail("vet2@gmail.com")) {
            vet = new Vet("vet2", "vet2@gmail.com", "vet2", "1234567890", "Athens", "Johny Doe",  "https://www.4icu.org/i/programs-courses-degrees/bachelor-of-veterinary-medicine-500x356.png");
            userService.saveUser(vet);
        }

        if (!userService.existsByEmail("adopter@gmail.com")) {
             adopter = new Adopter("adopter", "adopter@gmail.com", "adopter", "1245678903", "Athens,Tavros", "Jane Doe", LocalDate.now().minusYears(21), "https://t3.ftcdn.net/jpg/03/74/95/16/360_F_374951602_cmtwzq4Erge2HNa94YWDup1QII4IvRpO.jpg");
             adopter.setUserStatus(UserStatus.APPROVED);
             userService.saveUser(adopter);
        }

        if (!userService.existsByEmail("adopter2@gmail.com")) {
            adopter = new Adopter("adopter2", "adopter2@gmail.com", "adopter2", "1222228405", "Athens,Zografou", "John Doe", LocalDate.now().minusYears(24), "https://t4.ftcdn.net/jpg/02/32/92/21/360_F_232922178_YCAxIU0vlGoGY2H76ZsATswNrOVbWlUv.jpg");
            userService.saveUser(adopter);
        }
    }
}