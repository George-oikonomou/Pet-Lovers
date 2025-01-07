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
//    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Pet> pets;

//    @Column
//    @NotBlank
//    private Vet  vet;
// END TABLE COLUMNS


    public Shelter(String username, String email, String password, String contactNumber, String location, String name, List<AdoptionRequest> adoptionRequests) {
        super(username, email, password, contactNumber, location);
        this.name = name;
        this.adoptionRequests = adoptionRequests;
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
}
