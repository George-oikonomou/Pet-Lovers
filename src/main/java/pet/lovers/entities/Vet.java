package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "vets")
@PrimaryKeyJoinColumn(name = "user_id")
public class Vet extends User {

    @Column
    @NotBlank
    @Size(max = 60)
    private String fullName;

    @Column
    @NotBlank
    private String specialization;

//    @OneToMany(mappedBy = "vet")
//    @JoinColumn(name = "shelterID", referencedColumnName = "shelterID")
//    private Shelter shelter;

    public Vet() {
        super();
    }

    public Vet(Integer id, String username, String email, String password, long contact_number, String location, String role, Set<Role> roles, String fullName, String specialization) {
        super(id, username, email, password, contact_number, location, role, roles);
        this.fullName = fullName;
        this.specialization = specialization;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}