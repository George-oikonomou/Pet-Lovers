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
    private String name;


    @OneToMany(mappedBy = "shelter", cascade= CascadeType.ALL)
    private List<AdoptionRequest> adoptionRequests;
    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="vet_id")
    private Vet vet;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "documents", referencedColumnName = "id")
    private Document documents;
// END TABLE COLUMNS

    //CONSTRUCTORS
    public Shelter(String username, String email, String password, String contactNumber, String location, String name, List<AdoptionRequest> adoptionRequests, Document documents) {
        super(username, email, password, contactNumber, location);
        this.name = name;
        this.adoptionRequests = adoptionRequests;
        this.documents = documents;
    }

    public Shelter() {};

    //GETTERS AND SETTERS
    public  String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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

    public Document getDocuments() {
        return documents;
    }

    public void setDocuments(Document documents) {
        this.documents = documents;
    }
}
