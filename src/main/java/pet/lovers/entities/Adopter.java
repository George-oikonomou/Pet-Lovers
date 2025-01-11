package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "identification", referencedColumnName = "id")
    private Document identification;

    @OneToMany(mappedBy = "adopter", cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Visit> visits;

    @OneToMany(mappedBy = "adopter", cascade= CascadeType.ALL)
    private List<AdoptionRequest> adoptionRequests;
    //END TABLE COLUMNS

    //CONSTRUCTORS
    public Adopter() {}

    public Adopter(String username, String email, String password, String contactNumber, String location, String fullName, LocalDate birthDate, Document identification) {
        super(username, email, password, contactNumber, location);
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.identification = identification;
        super.setUserStatus(UserStatus.APPROVED); //adopter is automatically approved
    }

    //GETTERS AND SETTERS
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
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

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    @Override
    public String toString() {
        return "Adopter{" +
                "email='" + getEmail() + '\'' +
                "username='" + getUsername() + '\'' +
                "password='" + getPassword() + '\'' +
                "contactNumber='" + getContactNumber() + '\'' +
                "location=" + getLocation() +
                "fullName='" + fullName + '\'' +
                ", birthDate=" + birthDate +
                ", identification=" + identification +
                ", visits=" + visits +
                ", adoptionRequests=" + adoptionRequests +
                '}';
    }
}