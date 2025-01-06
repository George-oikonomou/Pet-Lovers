package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;

@Entity
@Table
public class Vet extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vetID;

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

    public Vet() {}

    public Vet(String username, String email, String password, int vetID, String fullName, String specialization) {
        super(username, email, password);
        this.vetID = vetID;
        this.fullName = fullName;
        this.specialization = specialization;
    }

    public int getVetID() {
        return vetID;
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