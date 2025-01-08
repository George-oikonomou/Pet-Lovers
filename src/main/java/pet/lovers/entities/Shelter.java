package pet.lovers.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

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
    private Set<AdoptionRequest> adoptionRequests;

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL)
    private Set<Pet> pets;


//    @Column
//    @NotBlank
//    private Vet  vet;
// END TABLE COLUMNS


    public Shelter(String username, String email, String password, String contactNumber, String location, String name) {
        super(username, email, password, contactNumber, location);
        this.name = name;
    }

    public Shelter() {
        super();
    }



    //GETTERS AND SETTERS
    public  String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Set<AdoptionRequest> getAdoptionRequests() {
        return adoptionRequests;
    }
    public void setAdoptionRequests(Set<AdoptionRequest> adoptionRequests) {
        this.adoptionRequests = adoptionRequests;
    }

    public Set<Pet> getPets() {
        return pets;
    }
    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }
}
