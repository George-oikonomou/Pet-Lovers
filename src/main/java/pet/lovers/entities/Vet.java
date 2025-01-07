package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "vets")
@PrimaryKeyJoinColumn(name = "user_id")
public class Vet extends User {


    // TABLE COLUMNS
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
    // END TABLE COLUMNS


    public Vet() {}

    public Vet(Integer id, String username, String email, String password, long contactNumber, String location, String fullName, String specialization) {
        super(id, username, email, password, contactNumber, location);
        this.fullName = fullName;
        this.specialization = specialization;
    }


    // GETTERS AND SETTERS
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
