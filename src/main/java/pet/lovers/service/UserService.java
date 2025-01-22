package pet.lovers.service;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.scheduling.annotation.Scheduled;
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
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {


    private final EmailService emailService;
    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
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
    public void updateUser(User user) {
        user = userRepository.save(user);
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
    public void updateUserDetails(User user, String email, String username, String fullName, String contactNumber, UserStatus userStatus) {
        user.setEmail(email);
        user.setUsername(username);
        user.setFullName(fullName);
        user.setContactNumber(contactNumber);
        user.setUserStatus(userStatus);

        this.updateUser(user);

        if (userStatus == UserStatus.APPROVED)
            emailService.sendRejectedUserMessage(user.getEmail(), user.getUsername());
        else if (userStatus == UserStatus.REJECTED)
            emailService.sendRejectedUserMessage(user.getEmail(), user.getUsername());
        else if (userStatus == UserStatus.PENDING)
            emailService.sendPendingUserMessage(user.getEmail(), user.getUsername());
    }

    @Transactional
    public Object getUsers() {
        return userRepository.findAll()
                             .stream()
                             .filter(user -> user.getRoles()
                                 .stream()
                                 .noneMatch(role -> role.toString().equals(Role.ADMIN))
                             )
                             .toList();
    }

    public User getUserByVerificationCode(String verificationCode) {
        return userRepository.findByVerificationCode(verificationCode).orElseThrow();
    }

    public Object getUser(Long userId) {
        return userRepository.findById(userId).get();
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName()).orElseThrow();
    }

    @Transactional
    public String resetUserPassword(User user, String password, String confirmPassword) {
        if (user.getVerificationCodeExpiration() != null && user.getVerificationCodeExpiration().isBefore(LocalDateTime.now())) {
            return "Expired verification code!";
        }else if (!password.equals(confirmPassword)) {
            return "Passwords do not match!";
        }

        user.setPassword(password);
        user.setVerificationCode(null);
        user.setVerificationCodeExpiration(null);
        saveUser(user);

        return null;
    }


    public String getUsersTmpGeneratedVerificationCode(User user) {
        String generatedPassword = generateRandomPassword();
        user.setVerificationCode(generatedPassword);
        user.setVerificationCodeExpiration(LocalDateTime.now().plusMinutes(25));
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
    @Scheduled(cron = "0 */5 * * * *")
    public void removeExpiredVerificationCodes() {
        userRepository.findAll().forEach(user -> {
            if (user.getVerificationCodeExpiration() != null && user.getVerificationCodeExpiration().isBefore(LocalDateTime.now())) {
                user.setVerificationCode(null);
                user.setVerificationCodeExpiration(null);
                userRepository.save(user);
            }
        });
    }
}