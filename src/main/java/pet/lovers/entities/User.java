package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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
    private String contact_number;

    @NotBlank
    @Size(max = 100)
    private String location;

    @NotBlank
    private Role role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(Integer id, String username, String email, String password, String contact_number, String location, Role role, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.contact_number = contact_number;
        this.location = location;
        this.role = role;
        this.roles = roles;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(@NotBlank @Pattern(regexp = "^\\d{10}$", message = "The number must be exactly 10 digits.") String contact_number) {
        this.contact_number = contact_number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(@NotBlank @Size(max = 100) String location) {
        this.location = location;
    }

    public String getRole() {
        return role;
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

    @Override
    public String toString() {
        return username;
    }
}
