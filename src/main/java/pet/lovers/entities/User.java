package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 20)
    private String username;


    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Pattern(regexp = "^\\d{10}$", message = "The number must be exactly 10 digits.")
    private String contactNumber;

    @Column
    @NotBlank
    private String location;

    @Column
    @NotBlank
    @Size(max = 1000)
    private String DocumentUrl;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.PENDING;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "verification_code_expiration")
    private LocalDateTime verificationCodeExpiration;

    public User() {}

    public User(String username, String email, String password, String contactNumber, String location, String DocumentUrl) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.location = location;
        this.DocumentUrl = DocumentUrl;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }
    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public String getDocumentUrl() {
        return DocumentUrl;
    }
    public void setDocumentUrl(String DocumentUrl) {
        this.DocumentUrl = DocumentUrl;
    }

    public String getVerificationCode() {
        return verificationCode;
    }
    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public LocalDateTime getVerificationCodeExpiration() {
        return verificationCodeExpiration;
    }
    public void setVerificationCodeExpiration(LocalDateTime VerificationCodeExpiration) {
        this.verificationCodeExpiration = VerificationCodeExpiration;
    }

    @Override
    public String toString() {
        return username;
    }

    public void setFullName(String fullName) {
        //default implementation (handled in runtime polymorphism)
    }
}
