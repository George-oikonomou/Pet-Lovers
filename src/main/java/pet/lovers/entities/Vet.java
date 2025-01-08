package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "identification", referencedColumnName = "id")
    private Document identification;

    @OneToMany(mappedBy = "vet", cascade= CascadeType.ALL)
    @JoinColumn(name = "shelterID", referencedColumnName = "shelterID")
    private List<Shelter> shelters;
    // END TABLE COLUMNS

    public Vet() {}

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
