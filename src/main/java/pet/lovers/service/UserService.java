package pet.lovers.service;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pet.lovers.entities.*;
import pet.lovers.repositories.RoleRepository;
import pet.lovers.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {


    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Integer saveUser(User user) {
         user.setPassword(passwordEncoder.encode(user.getPassword()));

         String roleName = user instanceof Vet     ? Role.VET     :
                           user instanceof Shelter ? Role.SHELTER : Role.ADOPTER;

        roleRepository.findByName(roleName).ifPresent(role -> {
            if (user.getRoles().isEmpty())
                user.getRoles().add(role);
        });

        return userRepository.save(user).getId();
    }

    @Transactional
    public Integer updateUser(User user) {
        user = userRepository.save(user);
        return user.getId();
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = userRepository.findByUsername(username);

        if(opt.isEmpty())
            throw new UsernameNotFoundException("User with email: " +username +" not found !");
        else {
            User user = opt.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    user.getRoles()
                            .stream()
                            .map(role-> new SimpleGrantedAuthority(role.toString()))
                            .collect(Collectors.toSet())
            );
        }
    }

    @Transactional
    public void updateUserDetails(User user, String email, String username, String fullName, String contactNumber) {
        user.setEmail(email);
        user.setUsername(username);

        if (user instanceof Adopter adopter) { //todo: change vet name to FullName??
            adopter.setFullName(fullName);
            adopter.setContactNumber(contactNumber);
        } else if (user instanceof Shelter shelter) {
            shelter.setName(fullName);
            shelter.setContactNumber(contactNumber);
        } else if (user instanceof Vet vet) {
            vet.setFullName(fullName);
            vet.setContactNumber(contactNumber);
        }

        this.updateUser(user);
    }

    @Transactional
    public Object getUsers() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream()
                        .noneMatch(role -> role.toString().equals(Role.ADMIN)))
                .collect(Collectors.toList());
    }
    public Object getUser(Long userId) {
        return userRepository.findById(userId).get();
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName()).orElseThrow();
    }


    public String getUsersGeneratedPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        String generatedPassword = generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(generatedPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);

        return generatedPassword;
    }

    private String generateRandomPassword() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(Character::isLetterOrDigit, Character::isAlphabetic, Character::isDigit)
                .build();

        return generator.generate(10);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}