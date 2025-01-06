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


    @OneToMany(mappedBy = "shelter", cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<AdoptionRequest> adoptionRequests;
//    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Pet> pets;

//    @Column
//    @NotBlank
//    private Vet  vet;
// END TABLE COLUMNS


    //CONSTRUCTORS
    public Shelter(String username, String email, String password, String name) {
        super(username, email, password);
        this.name = name;
    }

    public Shelter() {}


    //GETTERS AND SETTERS
    public  String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}