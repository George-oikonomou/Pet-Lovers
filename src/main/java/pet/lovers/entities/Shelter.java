package pet.lovers.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table
@PrimaryKeyJoinColumn(name = "user_id")
public class Shelter extends User{


    //TABLE COLUMNS
    @Column
    @NotBlank
    @Size(min = 2, max = 50)
    private String fullName;

    @OneToMany(mappedBy = "shelter",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdoptionRequest> adoptionRequests;

    @OneToMany(mappedBy = "shelter",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visit> visits;

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets;

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmploymentRequest> employmentRequests;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="vet_id")
    private Vet vet;
    // END TABLE COLUMNS


    //CONSTRUCTORS
    public Shelter(String username, String email, String password, String contactNumber, String location, String name,String documents) {
        super(username, email, password, contactNumber, location, documents);
        this.fullName = name;
    }

    public Shelter() {}

    //GETTERS AND SETTERS
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<AdoptionRequest> getAdoptionRequests() {
        return adoptionRequests;
    }
    public void setAdoptionRequests(List<AdoptionRequest> adoptionRequests) {
        this.adoptionRequests = adoptionRequests;
    }

    public List<Pet> getPets() {
        return pets;
    }
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public Vet getVet() {
        return vet;
    }
    public void setVet(Vet vet) {
        this.vet = vet;
    }

    public List<EmploymentRequest> getEmploymentRequests() {
        return employmentRequests;
    }
    public void setEmploymentRequests(List<EmploymentRequest> employmentRequests) {
        this.employmentRequests = employmentRequests;
    }

    public List<Visit> getVisits() {
        return visits;
    }
    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }
}
