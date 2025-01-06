package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CollectionIdMutability;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
@Table(name = "adopters")
@PrimaryKeyJoinColumn(name = "user_id")
public class Adopter extends User {


    //TABLE COLUMNS
    @Column
    @NotBlank
    @Size(max = 60)
    private String fullName;

    @Column
    @NotBlank
    private String birthDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "identification", referencedColumnName = "id")
    private Document identification;

    @OneToMany(mappedBy = "adopter", cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<AdoptionRequest> adoptionRequests;

//    @OneToMany(mappedBy = "vet")
//    @JoinColumn(name = "shelterID", referencedColumnName = "shelterID")
//    private Shelter shelter;
    //END TABLE COLUMNS

    //CONSTRUCTORS
    public Adopter() {}

    public Adopter(String username, String email, String password,String fullName, String birthDate, Document identification) {
        super(username, email, password);
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.identification = identification;
    }


    //GETTERS AND SETTERS
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Document getIdentification() {
        return identification;
    }

    public void setIdentification(Document identification) {
        this.identification = identification;
    }

    public List<AdoptionRequest> getAdoptionRequests() {
        return adoptionRequests;
    }

    public void setAdoptionRequests(List<AdoptionRequest> adoptionRequests) {
        this.adoptionRequests = adoptionRequests;
    }
}